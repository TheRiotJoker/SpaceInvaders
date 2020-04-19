import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
public class FunctionControl {
	private Timer moveEnemiesTimer;
	private LevelData levelData;
	private Timer graphicUpdateTimer;
	private int baseMovementDelay = 650;
	private int movementDelay = 0;
	private boolean firstTime = true;
	private boolean moveLeft = false;
	private boolean shoot = false;
	private boolean moveRight  = false;
	private ProtectionBox[][][] protectionBoxes;
	private int killedEnemiesCounter = 0;
	private PlayerShip player;
	private int aliveEnemies = 0;
	private boolean wonGame = false;
	private boolean lostGame = false;
	private UFO ufoShip;
	private EnemyShip[][] enemy;
	private String moveDirection; 
	private Gui gui;
	private KeyListener l = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent e) {
			if(firstTime && e.getKeyCode() == KeyEvent.VK_DOWN) {
				firstTime = false;
				gui.removeStartScreen();
				gui.makeProtectionBoxesVisible();
				startTimers();
			}
			if(lostGame && e.getKeyCode() == KeyEvent.VK_R) {
				resetGame();
			}
			if(!lostGame && player.isAlive()) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					moveLeft = true;
					break;
				case KeyEvent.VK_RIGHT:
					moveRight = true;
					break;
				case KeyEvent.VK_SPACE:
					shoot = true;
					break;
				}
			}	
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(!lostGame && player.isAlive()) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					moveLeft = false;
					break;
				case KeyEvent.VK_RIGHT:
					moveRight = false;
					break;
				case KeyEvent.VK_SPACE:
					shoot = false;
					break;
				}
			}
			
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	};
	/* 
	 * When an object of this class gets created in the gui, it does the following:
	 * 
	 * First creates the player and the enemies, giving them their locations
	 * then updates the screen, adds the key listener
	 * then starts the graphic update timer, which updates the screen every 5ms
	 * 
	 * 
	 * */
	public FunctionControl(Gui gui) {
		this.gui = gui;
		init();
		initLevelData();
		gui.addKeyListener(l);
		gui.startScreen();
	}
	public void resetGame() {
		gui.setGamePanel();
		if(lostGame) {
			initLevelData();
			gui.updateScoreDisplay(" ");
			lostGame = false;
		}
		if(wonGame) {
			wonGame = false;
			levelData.incrementLives();
			gui.updateLivesDisplay(levelData.getLives());
		}
		if(baseMovementDelay >= 250) {
			baseMovementDelay -= 35;	
		}
		gui.destroyEnemyLabels();
		gui.updateUfoLabel(new Rectangle(0,0,0,0));
		if(ufoShip.isActive()) {
			ufoShip.destroyedUfO();
		}
		gui.cancelTimer();
		gui.initEnemyLabels();
		init();
	}
	public void initLevelData() {
		int horizontalAlignment = 18; //this variable will be used to place the boxes inside the protection shields
		int verticalAlignment = 335; //this one as well
		int horizontalPush = 18; //this variable will be used to push the horizontal placement right when making different shields
		levelData = new LevelData();
		gui.updateLivesDisplay(levelData.getLives());
		gui.updateUfoLabelImage(ufoShip.getTexture());
		protectionBoxes = new ProtectionBox[4][4][4];
		protectionBoxes[0][3] = new ProtectionBox[2];
		protectionBoxes[1][3] = new ProtectionBox[2];
		protectionBoxes[2][3] = new ProtectionBox[2];
		protectionBoxes[3][3] = new ProtectionBox[2];
		for(int i = 0; i < protectionBoxes.length; i++) {
			for(int j = 0; j < protectionBoxes[i].length; j++) {
				for(int k = 0; k < protectionBoxes[i][j].length; k++) {
					horizontalAlignment += ProtectionBox.getWidthDimensions();
					if(j == 3 && k == 1) {
						protectionBoxes[i][j][k] = new ProtectionBox(protectionBoxes[i][2][3].getHitbox().x,verticalAlignment);
					} else {
						protectionBoxes[i][j][k] = new ProtectionBox(horizontalAlignment, verticalAlignment);
					}
					gui.createProtectionBoxes(i, j, k, protectionBoxes[i][j][k].getCurrentTexture(), protectionBoxes[i][j][k].getHitbox());
				}
				verticalAlignment += ProtectionBox.getHeightDimensions();
				horizontalAlignment = horizontalPush;	
			}
			verticalAlignment = 335;
			horizontalPush += 150;
			horizontalAlignment = horizontalPush;
		}
	}
	private void cancelAllTimers() {
		graphicUpdateTimer.cancel();
		graphicUpdateTimer.purge();
		moveEnemiesTimer.cancel();
		moveEnemiesTimer.purge();
	}
	
	private void init() {
		ufoShip = new UFO();
		player = new PlayerShip(200,410);
		movementDelay = baseMovementDelay;
		gui.updateLivesImage(player.getPlayerModel());
		int x = 0;
		int y = 80;
		enemy = new EnemyShip[5][11];
		aliveEnemies = enemy.length * enemy[0].length;
		for(int i = 0; i < enemy.length; i++) {
			for(int j = 0; j < enemy[i].length; j++) {
				if(i == 0) {
					enemy[i][j] = new SquidUfoShip(x,y);
				} else {
					if(i <=2 ) {
						enemy[i][j] = new AlienShip(x,y);
					} else {
						enemy[i][j] = new UfoShip(x,y);
						if(i == enemy.length-1) {
							enemy[i][j].setLowest(true);
						}
					}
				}
				x = x+38;
			}
			x = 0;
			y = y+38;
		}
		if(firstTime) {
			for(int i = 0; i < enemy.length; i++) {
				for(int j = 0; j < enemy[i].length; j++) {
					gui.changeLabelVisibility(false);
					gui.updateEnemyLabel(i,j,enemy[i][j].getHitbox(), enemy[i][j].getTexture());
					
				}
			}
		}
		getRightmostEnemy();
		getLeftmostEnemy();
		moveDirection = "right";
		if(!firstTime) {
			gui.makeProtectionBoxesVisible();
			startTimers();	
		}
	}
	private void startTimers() {
		startGraphicUpdateTimer();
		startEnemyMovementTimer();
	}

	private void startGraphicUpdateTimer() {
		graphicUpdateTimer = new Timer();
		graphicUpdateTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				updateGraphics();
			}
			
		}, 0, 32);
	}
	private void updateGraphics() {
		if(moveRight) {
			player.moveRight();
		}
		if(moveLeft) {
			player.moveLeft();
		}
		if(shoot) {
			player.shoot();
		}
		gui.updatePlayerSprite(player.getPlayerHitbox(), player.getPlayerModel());
		gui.updatePlayerProjectile(player.drawProjectile());
		for(int i = 0; i < enemy.length; i++) {
			for(int j = 0; j < enemy[i].length; j++) {
				gui.updateEnemyProjectileLabel(i, j, enemy[i][j].getEnemyProjectile().getProjectileHitbox());
				if(enemy[i][j].getEnemyProjectile().isActive()) {
					if(enemy[i][j].getEnemyProjectile().getProjectileHitbox().intersects(player.getPlayerHitbox()) && player.isAlive()) {
						enemy[i][j].getEnemyProjectile().stopProjectile();
						levelData.decrementLives();
						moveRight = false;
						shoot = false;
						moveLeft = false;
						gui.updateLivesDisplay(levelData.getLives());
						player.playerDied(levelData.getLives());
						if(levelData.getLives() == 0) {
							loseGame();
						}
					} else {
						checkForProtectionBoxHits(i, j);
					}
				}
			}
		}
		if(player.getPlayerProjectile().isActive()) {
			checkForProjectileHits();
		}
		if(ufoShip.isActive()) {
			gui.updateUfoLabel(ufoShip.getUfoHitbox());
		}
		
	}
	private void checkForProtectionBoxHits(int x, int y) {
		for(int i = 0; i < protectionBoxes.length; i++) {
			for(int j = 0; j < protectionBoxes[i].length; j++) {
				for(int k = 0; k < protectionBoxes[i][j].length; k++) {
					if(protectionBoxes[i][j][k].isAlive()) {
						if(protectionBoxes[i][j][k].getHitbox().intersects(enemy[x][y].getEnemyProjectile().getProjectileHitbox())) {
							enemy[x][y].getEnemyProjectile().stopProjectile();
							protectionBoxes[i][j][k].reduceHp();
							gui.setProtectionBoxesTexture(i, j, k, protectionBoxes[i][j][k].getCurrentTexture());
						}
					}
				}
			}
		}
	}
	private void checkForProjectileHits() {
		int temp = 0;
		if(player.getPlayerProjectile().getProjectileHitbox().intersects(ufoShip.getUfoHitbox())) {
			temp = ufoShip.destroyedUfO();
			levelData.increaseScore(temp);
			gui.updateScoreDisplay(Integer.toString(levelData.getScore()));
			gui.updateUfoLabel(ufoShip.getUfoHitbox());
			gui.ufoDestroyedAnim(player.getPlayerProjectile().getProjectileHitbox(), temp);
			player.getPlayerProjectile().stopProjectile();
			return;
		}
		for(int i = 0; i < protectionBoxes.length; i++) {
			for(int j = 0; j < protectionBoxes[i].length; j++) {
				for(int k = 0; k < protectionBoxes[i][j].length; k++) {
					if(protectionBoxes[i][j][k].isAlive()) {
						if(protectionBoxes[i][j][k].getHitbox().intersects(player.getPlayerProjectile().getProjectileHitbox())) {
							player.getPlayerProjectile().stopProjectile();
							protectionBoxes[i][j][k].reduceHp();
							gui.setProtectionBoxesTexture(i, j, k, protectionBoxes[i][j][k].getCurrentTexture());
							return;
						}
					}
				}
			}
		}
		for(int i = 0; i < enemy.length; i++) {
			for(int j = 0; j < enemy[i].length; j++) {
					if(player.getPlayerProjectile().getProjectileHitbox().intersects(enemy[i][j].getHitbox())) {
					levelData.increaseScore(enemy[i][j].killShip());
					increaseKilledEnemiesCounter();
					if(enemy[i][j].isLowest()) {
						findNextLowestEnemy(j);
					}
					if(enemy[i][j].isLeftmost() ) {
						enemy[i][j].setLeftmost(false);
						getLeftmostEnemy();
					} else {
						if(enemy[i][j].isRightmost()) {
							enemy[i][j].setRightmost(false);
							getRightmostEnemy();
						}
					}
					player.getPlayerProjectile().stopProjectile();
					gui.destroyShipAnim(i, j);
					gui.updateScoreDisplay(Integer.toString(levelData.getScore()));
					reduceAliveEnemies();
					return;
				}

			}
		}
	}
	private void reduceAliveEnemies() {
		aliveEnemies--;
		if(aliveEnemies == 0) {
			cancelAllTimers();
			wonGame = true;
			resetGame();
		}
	}
	private void increaseKilledEnemiesCounter() {
		killedEnemiesCounter++;
		if(killedEnemiesCounter == 10) {
			killedEnemiesCounter = 0;
			if(movementDelay-30 >= 50) {
				movementDelay -= 30;	
				moveEnemiesTimer.cancel();
				moveEnemiesTimer.purge();
				startEnemyMovementTimer();
			}
		}
	}
	private void startEnemyMovementTimer() {
		moveEnemiesTimer = new Timer();
		moveEnemiesTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				//moveDirection gets set based on the position of the objects
				//if the position of the objects allows for them to keep moving 
				//in the direction of moveDirection - it won't get edited
				moveDirection = moveEnemies(moveDirection);
				if(ufoShip.isActive() == false) {
					ufoShip.generateUFO();
				}
				for(int i = 0; i < enemy.length; i++) {
					for(int j = 0; j < enemy[i].length; j++) {
						gui.updateEnemyLabel(i,j,enemy[i][j].getHitbox(), enemy[i][j].getTexture());	
						if(enemy[i][j].isAlive() && enemy[i][j].isLowest()) {
							enemy[i][j].shoot();
						}
					}
				}
			}
		}, 0, movementDelay);
	}
	private String moveEnemies(String direction) {
		String retDirection = direction;
		boolean movedDown = false;
		for(int i = 0; i < enemy.length; i++) {
			 for(int j = 0; j < enemy[i].length; j++) {	 
				if(enemy[i][j].isAlive()) { //if enemy is alive - move it with the following criteria
						if(direction.contentEquals("right")) { 
							if(enemy[i][j].moveRight().contentEquals("Border_Reached")) { //move enemy to the right and check if it's at the border
								retDirection = "downLeft"; //if it is at the border, set it to downLeft downleft makes it move down and then decides that after moving down the direction is left
							}
						} else {
							if(direction.contentEquals("left")) {
								if(enemy[i][j].moveLeft().contentEquals("Border_Reached")) { //moves the object to the left and if the border is reached, sets the direction to
									retDirection = "downRight"; //downRight - so that the objects move down once and then go right afterwards
								}	
							}
							else {
								movedDown = true;
								enemy[i][j].getHitbox().y += 5;
								if(enemy[i][j].getHitbox().y >= 360) {
									loseGame();
								}
								if(direction.contentEquals("downLeft")) {
									retDirection = "left";
								} else {
									retDirection = "right";
								}
							}
						}
					}
				}
			}
		if(movedDown && movementDelay-65 > 50) {
			movementDelay -= 65;
			moveEnemiesTimer.cancel();
			moveEnemiesTimer.purge();
			startEnemyMovementTimer();
		}
		return retDirection; //retDirection is basically the direction that the objects will move in the next time
	}
	private void getRightmostEnemy() {
		int x = 0;
		int y = 0;
		int maxX = 0;
		for(int i = enemy.length-1; i >= 0; i--) {
			for(int j = enemy[i].length-1; j >= 0; j--) {
				if(enemy[i][j].getHitbox().x >= maxX && enemy[i][j].isAlive()) {
					maxX = enemy[i][j].getHitbox().x;
					x = i;
					y = j;
				}
			}
		}
		
		enemy[x][y].setRightmost(true);
		
	}
	private void findNextLowestEnemy(int x) {
		for(int i = enemy.length-1; i >= 0; i--) {
			if(enemy[i][x].isAlive()) {
				enemy[i][x].setLowest(true);
				return;
			}
		}
		
	}
	private void getLeftmostEnemy() {
		int x = 0;
		int y = 0;
		int maxX = 999;
		
		for(int i = enemy.length-1; i >= 0; i--) {
			for(int j = 0; j < enemy[i].length; j++) {
				if(enemy[i][j].getHitbox().x <= maxX && enemy[i][j].isAlive()) {
					maxX = enemy[i][j].getHitbox().x;
					x = i;
					y = j;
				}
			}
		}
		enemy[x][y].setLeftmost(true);
	}
	private void loseGame() {
		lostGame = true;
		gui.setLoseGamePanel(levelData.getScore());
		cancelAllTimers();
		//TODO: Implement gui for this
	}
}
