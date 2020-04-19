import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PlayerShip {
	private Rectangle playerHitbox;
	private ImageIcon playerModel;
	private ImageIcon deathModel;
	private ImageIcon deathModel2;
	private int counter = 0;
	private boolean alive = true;
	final private int playerWidth = 45;
	final private int playerHeight = 25;
	private Projectile projectile;
	public PlayerShip(int x, int y) {
		BufferedImage playerModelToEdit = null;
		projectile = new PlayerProjectile(3,10,3);
		playerHitbox = new Rectangle(x,y,playerWidth,playerHeight);
		try {
			playerModelToEdit = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\playerModel.png"));
			playerModel = new ImageIcon(playerModelToEdit.getScaledInstance(playerWidth, playerHeight, Image.SCALE_DEFAULT));
			playerModelToEdit = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\deadShip.png"));
			deathModel = new ImageIcon(playerModelToEdit.getScaledInstance(playerWidth, playerHeight, Image.SCALE_DEFAULT));
			playerModelToEdit = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\deadShip2.png"));
			deathModel2 = new ImageIcon(playerModelToEdit.getScaledInstance(playerWidth, playerHeight, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean isAlive() {
		return alive;
	}
	public ImageIcon getPlayerModel() {
		return playerModel;
	}
	public Rectangle getPlayerHitbox() {
		return playerHitbox;
	}
	public void moveLeft() {
		if(playerHitbox.x-10 > 0) {
			playerHitbox.x -= 10;	
		} else {
			playerHitbox.x = 0;
		}
	}
	public void moveRight() {
		if(playerHitbox.x+playerHitbox.getWidth()+12 < 640) {
			playerHitbox.x += 10;	
		} else {
			playerHitbox.x = 640-playerWidth;
		}
	}
	public void shoot() {
		if(projectile.isActive() == false) {
			projectile.fireShot((playerWidth/2)+playerHitbox.x, playerHitbox.y-15);
		}
	}
	public Rectangle drawProjectile() {
		if(projectile.isActive() == true ) {
			return projectile.getProjectileHitbox();
		}
		return null;
	}
	public Projectile getPlayerProjectile() {
		return projectile;
	}
	public void playerDied(int lives) {
		ImageIcon playerModelSave = playerModel;
		alive = false;
		if(lives == 0) {
			playerModel = deathModel;
		} else {
			counter = 0;
			Timer t = new Timer();
			t.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					counter++;
					if(playerModel.equals(deathModel)) {
						playerModel = deathModel2;
					} else {
						playerModel = deathModel;
					}
					if(counter == 25) {
						playerModel = playerModelSave;
						alive = true;
						t.cancel();
						t.purge();
						return;
					}
					
				}
				
			}, 0, 100);
		}
	}
}
