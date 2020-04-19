import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ProtectionBox {
	private int hp;
	private static final int widthDIMENSIONS = 25;
	private static final int heightDIMENSIONS = 15;
	private Rectangle protectionHitbox;
	private ImageIcon currentTexture;
	private boolean alive;
	public ProtectionBox(int x, int y) {
		hp = 4;
		alive = true;
		protectionHitbox = new Rectangle(x,y,widthDIMENSIONS, heightDIMENSIONS);
		currentTexture = new ImageIcon(scaleImageIcon("D:\\Java\\Space Invaders\\src\\squareFullHp.png"));
	}
	public ImageIcon getCurrentTexture() {
		return currentTexture;
	}
	public void reduceHp() {
		hp--;
		String path = null;
		if(hp != 0) {
			path = "D:\\Java\\Space Invaders\\src\\square"+hp+"Hp.png";	
			currentTexture = new ImageIcon(scaleImageIcon(path));
		} else {
			alive = false;
			protectionHitbox = new Rectangle(0,0,0,0);
			currentTexture = null;
		}
	}
	private Image scaleImageIcon(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img.getScaledInstance(widthDIMENSIONS, heightDIMENSIONS, Image.SCALE_DEFAULT);
	}
	public Rectangle getHitbox() {
		return protectionHitbox;
	}
	public static final int getWidthDimensions() {
		return widthDIMENSIONS;
	}
	public static final int getHeightDimensions() {
		return heightDIMENSIONS;
	}
	public boolean isAlive() {
		return alive;
	}
}
