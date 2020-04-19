import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
public class Gui extends JFrame {
	private static final long serialVersionUID = -2773255008429265256L;
	@SuppressWarnings("unused")
	private FunctionControl control;
	private JLabel playerLabel;
	private JPanel scoringPanel;
	private JPanel gamePanel;
	private JLabel playerProjectileLabel;
	private JLabel lineLabel;
	private JLabel scoreLabel;
	private JLabel ufoLabel;
	private JLabel ufoScoringEventLabel;
	private JLabel livesText;
	private int counter = 0;
	private JTextField lostTextField;
	private JTextField lostTextFieldRestartTip;
	private JLabel[] livesImageLabel;
	private JLabel[][] enemyLabel;
	private ImageIcon destroyedTexture;
	private boolean cancelTimer = false;
	private JTextField spaceInvadersLogo;
	private JLabel[] enemyDisplayLabel;
	private JTextField[] tooltipText;
	private JTextField startText;
	private JLabel[][] enemyProjectileLabel;
	private JLabel[][][] protectionBoxLabels;
	public Gui() {
		int x = 400;
		setSize(640,480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);
		setTitle("Space Invaders");
		setLayout(null);
		gamePanel = new JPanel();
		gamePanel.setBounds(0,0, 640, 480);
		gamePanel.setLayout(null);
		setContentPane(gamePanel);
		getContentPane().setBackground(Color.black);
		requestFocus();
		requestFocusInWindow();
		initEnemyLabels();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\explosion1.png"));
			destroyedTexture = new ImageIcon(img.getScaledInstance(30,24, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		spaceInvadersLogo = new JTextField();
		enemyDisplayLabel = new JLabel[4];
		tooltipText = new JTextField[4];
		startText = new JTextField();
		
		
		ufoLabel = new JLabel();
		ufoScoringEventLabel = new JLabel();
		ufoScoringEventLabel.setForeground(Color.white);
		ufoScoringEventLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
		add(ufoScoringEventLabel);
		add(ufoLabel);
		livesText = new JLabel("L I V E S: ");
		livesText.setForeground(Color.white);
		livesText.setFont(new Font("Helvetica", Font.BOLD, 14));
		livesText.setBounds(x,20,100,18);
		add(livesText);
		
		scoreLabel = new JLabel("S C O R E: ");
		scoreLabel.setForeground(Color.white);
		scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
		scoreLabel.setBounds(10,20,200,18);
		add(scoreLabel);
		
		lineLabel = new JLabel();
		lineLabel.setBackground(new Color(0, 153, 51));
		lineLabel.setOpaque(true);
		lineLabel.setBounds(0,440,640,5);
		add(lineLabel);
		
		playerLabel = new JLabel();
		
		scoringPanel = new JPanel();
		scoringPanel.setBounds(0,0,getWidth(), getHeight());
		scoringPanel.setOpaque(true);
		scoringPanel.setBackground(Color.black);
		
		lostTextField = new JTextField();
		lostTextField.setEditable(false);
		lostTextField.setForeground(Color.white);
		lostTextField.setBackground(Color.black);
		lostTextField.setFont(new Font("Helvetica", Font.BOLD, 32));
		lostTextField.setHorizontalAlignment(SwingConstants.CENTER);
		lostTextField.setBounds(0,200,640,50);
		
		lostTextFieldRestartTip = new JTextField();
		lostTextFieldRestartTip.setEditable(false);
		lostTextFieldRestartTip.setForeground(Color.white);
		lostTextFieldRestartTip.setBackground(Color.black);
		lostTextFieldRestartTip.setFont(new Font("Helvetica", Font.BOLD, 32));
		lostTextFieldRestartTip.setHorizontalAlignment(SwingConstants.CENTER);
		lostTextFieldRestartTip.setBounds(0,300,640,50);
		
		
		scoringPanel.setLayout(null);
		scoringPanel.add(lostTextField);
		scoringPanel.add(lostTextFieldRestartTip);
		
		playerProjectileLabel = new JLabel();
		playerProjectileLabel.setBackground(Color.green);
		playerProjectileLabel.setOpaque(true);
		add(playerProjectileLabel);
		
		livesImageLabel = new JLabel[3];
		x = 470;
		for(int i = 0; i < livesImageLabel.length; i++) {
			livesImageLabel[i] = new JLabel();
			livesImageLabel[i].setBounds(x,10,45,25);
			x = x+51;
			
		}
		protectionBoxLabels = new JLabel[4][4][4];
		protectionBoxLabels[0][3] = new JLabel[2];
		protectionBoxLabels[1][3] = new JLabel[2];
		protectionBoxLabels[2][3] = new JLabel[2];
		protectionBoxLabels[3][3] = new JLabel[2];
		control = new FunctionControl(this);
		add(playerLabel);
		repaint();
	}
	public void createProtectionBoxes(int i, int j, int k, ImageIcon texture, Rectangle r) {
		protectionBoxLabels[i][j][k] = new JLabel();
		setProtectionBoxesTexture(i,j,k,texture);
		protectionBoxLabels[i][j][k].setVisible(false);
		protectionBoxLabels[i][j][k].setBounds(r);
		add(protectionBoxLabels[i][j][k]);
		repaint();
	}
	public void makeProtectionBoxesVisible() {
		for(int i = 0; i < protectionBoxLabels.length; i++) {
			for(int j = 0; j < protectionBoxLabels[i].length; j++) {
				for(int k = 0; k < protectionBoxLabels[i][j].length; k++) {
					protectionBoxLabels[i][j][k].setVisible(true);
				}
			}
		}
	}
	public void setProtectionBoxesTexture(int i, int j, int k, ImageIcon texture) {
		if(texture != null) {
			protectionBoxLabels[i][j][k].setIcon(texture);
		} else {
			protectionBoxLabels[i][j][k].setVisible(false);
		}
		repaint();
	}
	public void startScreen() {
		int y = 150;
		spaceInvadersLogo.setText("SPACE INVADERS");
		spaceInvadersLogo.setBounds(0,100,640,50);
		spaceInvadersLogo.setFont(new Font("Constantia", Font.BOLD, 32));
		spaceInvadersLogo.setHorizontalAlignment(SwingConstants.CENTER);
		spaceInvadersLogo.setBorder(null);
		spaceInvadersLogo.setEditable(false);
		spaceInvadersLogo.setBackground(Color.black);
		spaceInvadersLogo.setForeground(new Color(128,128,255));
		add(spaceInvadersLogo);
		for(int i = 0; i < enemyDisplayLabel.length; i++) {
			enemyDisplayLabel[i] = new JLabel();
			enemyDisplayLabel[i].setBounds(260,y,enemyLabel[0][0].getWidth(), enemyLabel[0][0].getHeight());
			tooltipText[i] = new JTextField();
			tooltipText[i].setBounds(310,y,100,20);
			tooltipText[i].setForeground(Color.white);
			tooltipText[i].setBackground(Color.black);
			tooltipText[i].setBorder(null);
			tooltipText[i].setEditable(false);
			tooltipText[i].setFont(new Font("Helvetica", Font.BOLD, 16));
			add(tooltipText[i]);
			add(enemyDisplayLabel[i]);
			y = y+enemyLabel[0][0].getHeight()+10;
			if(i == 2) {
				enemyDisplayLabel[i].setIcon(enemyLabel[3][0].getIcon());
			} else {
				if(i == 3) {
					enemyDisplayLabel[i].setBounds(235, y-35, 70,22);
				}
				enemyDisplayLabel[i].setIcon(enemyLabel[i][0].getIcon());
			}
		}
		enemyDisplayLabel[3].setIcon(ufoLabel.getIcon());
		tooltipText[0].setText(" = 10 PTS ");
		tooltipText[1].setText(" = 20 PTS ");
		tooltipText[2].setText(" = 40 PTS ");
		tooltipText[3].setText(" = ??? PTS ");
		startText.setBounds(200,y+50,235,35);
		startText.setForeground(Color.white);
		startText.setBackground(Color.black);
		startText.setBorder(null);
		startText.setEditable(false);
		startText.setFont(new Font("Helvetica", Font.ITALIC, 25));
		startText.setText("Press DOWN to play");
		add(startText);
		for(int i = 0; i < enemyLabel.length; i++) {
			for(int j = 0; j < enemyLabel[i].length; j++) {
				enemyLabel[i][j].setVisible(false);
			}
		}
		repaint();
	}
	public void removeStartScreen() {
		for(int i = 0; i < tooltipText.length; i++) {
			remove(tooltipText[i]);
			tooltipText[i] = null;
			remove(enemyDisplayLabel[i]);
			enemyDisplayLabel[i] = null;
		}
		remove(spaceInvadersLogo);
		remove(startText);
		for(int i = 0; i < enemyLabel.length; i++) {
			for(int j = 0; j < enemyLabel[i].length; j++) {
				enemyLabel[i][j].setVisible(true);
			}
		}
		repaint();
	}
	public void changeLabelVisibility(boolean visible) {
		for(int i = 0; i < enemyLabel.length; i++) {
			for(int j = 0; j < enemyLabel[i].length; j++) {
				enemyLabel[i][j].setVisible(visible);
			}
		}
	}
	public void setLoseGamePanel(int score) { 
		lostTextField.setText("You lost! Score: "+score);
		lostTextFieldRestartTip.setText("Press R to restart");
		this.remove(gamePanel);
		setContentPane(scoringPanel);
	}
	public void setGamePanel() {
		remove(scoringPanel);
		setContentPane(gamePanel);
		repaint();
	}
	public void updateLivesImage(ImageIcon icon) {
		for(int i = 0; i < livesImageLabel.length; i++) {
			livesImageLabel[i].setIcon(icon);
		}
	}
	public void initEnemyLabels() {
		enemyLabel = new JLabel[5][11];
		enemyProjectileLabel = new JLabel[5][11];
		for(int i = 0; i < enemyLabel.length; i++) {
			for(int j = 0; j < enemyLabel[i].length; j++) {
				enemyLabel[i][j] = new JLabel();
				enemyLabel[i][j].setVisible(true);
				enemyProjectileLabel[i][j] = new JLabel();
				enemyProjectileLabel[i][j].setVisible(false);
				enemyProjectileLabel[i][j].setOpaque(true);
				enemyProjectileLabel[i][j].setBackground(Color.white);
				add(enemyLabel[i][j]);
				add(enemyProjectileLabel[i][j]);
			}
		}
		repaint();
	}
	public void destroyEnemyLabels() {
		for(int i = 0; i < enemyLabel.length; i++) {
			for(int j = 0; j < enemyLabel[i].length; j++) {
				remove(enemyLabel[i][j]);
				remove(enemyProjectileLabel[i][j]);
			}
		}
		repaint();
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Gui gui = new Gui();
	}
	public void updatePlayerSprite(Rectangle r, ImageIcon i) {
		playerLabel.setBounds(r);
		playerLabel.setIcon(i);
	}
	public JLabel getPlayerLabel() {
		return playerLabel;
	}
	public void updatePlayerProjectile(Rectangle r) {
		if(r != null) {
			playerProjectileLabel.setBounds(r);
		} else {
			playerProjectileLabel.setBounds(0,0,0,0);
		}
	}
	public JLabel getPlayerProjectileLabel() {
		return playerProjectileLabel;
	}
	public void updateEnemyLabel(int x, int y, Rectangle r, ImageIcon i) {
		enemyLabel[x][y].setBounds(r);
		enemyLabel[x][y].setIcon(i);
	}
	public void updateEnemyProjectileLabel(int x, int y, Rectangle r) {
		enemyProjectileLabel[x][y].setBounds(r);
		enemyProjectileLabel[x][y].setVisible(true);
	}
	public void updateLivesDisplay(int lives) {
		for(int i = 0; i < livesImageLabel.length; i++) {
			remove(livesImageLabel[i]);
		}
		if(lives <= 3) {
			livesText.setText("L I V E S: ");
			
			for(int i = 0; i < lives; i++) {
				add(livesImageLabel[i]);
			}	
		} else {
			livesText.setText("L I V E S: x"+lives);
		}
		repaint();
		
	}
	public void destroyShipAnim(int x, int y) {
		Timer tempTimer = new Timer();
		enemyLabel[x][y].setIcon(destroyedTexture);
		tempTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(cancelTimer) {
					cancelTimer = false;
				} else {
					enemyLabel[x][y].setIcon(null);
					enemyLabel[x][y].setBounds(0,0,0,0);
					remove(enemyLabel[x][y]);	
				}
				
			}
			
		}, 200);
	}
	public void ufoDestroyedAnim(Rectangle r, int toDisplay) {
		Timer tempTimer = new Timer();
		counter = 0;
		lostTextField.setText(Integer.toString(toDisplay));
		ufoScoringEventLabel.setBounds(r.x-10, r.y-20, 50,20);
		ufoScoringEventLabel.setText(Integer.toString(toDisplay));
		tempTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				counter++;
				if(counter <= 5) {
					if(ufoScoringEventLabel.getText().contentEquals(Integer.toString(toDisplay))) {
						ufoScoringEventLabel.setText("");
					} else {
						ufoScoringEventLabel.setText(Integer.toString(toDisplay));
					}
				} else {
					ufoScoringEventLabel.setText(Integer.toString(toDisplay));
				}
				if(counter == 9) {
					ufoScoringEventLabel.setBounds(0,0,0,0);
					tempTimer.cancel();
					tempTimer.purge();
				}
				
			}
			
		}, 0, 95);	
	}
	public void cancelTimer() {
		cancelTimer = true;
	}
	public void updateScoreDisplay(String scoreToDisplay) {
		scoreLabel.setText("S C O R E: "+scoreToDisplay);
	}
	public void updateUfoLabel(Rectangle r) {
		ufoLabel.setBounds(r);
	}
	public void updateUfoLabelImage(ImageIcon i) {
		ufoLabel.setIcon(i);;
	}
}
