import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UfoShip extends EnemyShip{ //3rd strongest
	public UfoShip(int x, int y) {
		super(x, y);
		shootFactor = 100;
		projectile = new EnemyProjectile(8,10,7);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\weakAlien.png"));
			firstImage = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
			img = ImageIO.read(new File("D:\\Java\\Space Invaders\\src\\weakAlien2.png"));
			secondImage = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int killShip() {
		alive = false;		
		hitbox.setBounds(new Rectangle(-5,-5,0,0));
		return 10;
	}


}
