import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.util.Random;
public class UFO {
	private Rectangle ufoHitbox;
	private Timer moveTimer;
	private int width = 70;
	private int height = 22;
	private Random random;
	private ImageIcon texture;
	private boolean active;
	public UFO() { 
		ufoHitbox = new Rectangle(0,0,0,0);
		random = new Random();
		active = false;
		try {
			BufferedImage img = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\ufo.png"));
			texture = new ImageIcon(img.getScaledInstance(70, 22, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Rectangle getUfoHitbox() {
		return ufoHitbox;
	}
	public boolean isActive() {
		return active;
	}
	public ImageIcon getTexture() {
		return texture;
	}
	public void generateUFO() {
		int a = 0;
		a = random.nextInt(10)+1;
		if(random.nextInt(11) == 10) {
			active = true;
			if(a % 2 == 0) {
				ufoHitbox = new Rectangle(-width, 50, width, height);
				moveUFO("forwards");
			} else {
				ufoHitbox = new Rectangle(640, 50, width, height);
				moveUFO("backwards");
			}
		}
	}
	private void moveUFO(String direction) {
		moveTimer = new Timer();
		if(direction.contentEquals("forwards")) {
			moveTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					ufoHitbox.x++;
					if(ufoHitbox.x > 645) {
						stopTimer();
					}
				}
				
			}, 0, 8);
		} else {
			moveTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					ufoHitbox.x--; 
					if(ufoHitbox.x < -width-5) {
						stopTimer();
					}
				}
				
			}, 0, 8);
		}
	}
	private void stopTimer() {
		moveTimer.cancel();
		moveTimer.purge();
		active = false;
	}
	public int destroyedUfO() {
		stopTimer();
		ufoHitbox = new Rectangle(0,0,0,0);
		return (random.nextInt(3)+1) * 50;
	}
}
