import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public abstract class EnemyShip {
	protected Rectangle hitbox;
	protected ImageIcon firstImage;
	protected ImageIcon secondImage;
	private ImageIcon destroyedTexture;
	private ImageIcon iconToDisplay;
	protected int width = 30;
	protected int height = 26;
	private Random random;
	protected boolean alive;
	protected int shootFactor;
	protected Projectile projectile;
	private boolean rightmost = false;
	private boolean leftmost = false;
	private boolean lowest = false;
	public EnemyShip(int x, int y) {
		iconToDisplay = new ImageIcon();
		hitbox = new Rectangle(x,y,width,height);
		random = new Random();
		alive = true;
	}
	public ImageIcon getDestroyedTexture() {
		return destroyedTexture;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public boolean isAlive() {
		return alive;
	}
	public abstract int killShip();
	public boolean isRightmost() {
		return rightmost;
	}
	public void setRightmost(boolean rightmost) {
		this.rightmost = rightmost;
	}
	public boolean isLeftmost() {
		return leftmost;
	}
	public void setLeftmost(boolean leftmost) {
		this.leftmost = leftmost;
	}
	public String moveRight() {
		hitbox.x += 5;
		if(rightmost && hitbox.x+hitbox.width >= 630) {
			return "Border_Reached";
		} else {
			return "Success";
		}
	}
	public boolean isLowest() {
		return lowest;
	}
	public void setLowest(boolean lowest) {
		this.lowest = lowest;
	}
	public String moveLeft() {
		hitbox.x -= 5;
		if(leftmost && hitbox.x <= 0) {
			return "Border_Reached";
		} else {
			return "Success";
		}
	}
	public void shoot() {
		if(projectile.isActive() == false) {
			if(random.nextInt(shootFactor) == 10) {
				projectile.fireShot(hitbox.x+(int)hitbox.getWidth()/2, hitbox.y+5);
			}	
		}
	}
	public Projectile getEnemyProjectile() {
		return projectile;
	}
	public ImageIcon getTexture() {
		changeTexture();
		return iconToDisplay;
	}
	private void changeTexture() {
		if(iconToDisplay.equals(firstImage)) {
			iconToDisplay = secondImage;
		} else {
			iconToDisplay = firstImage;
		}
	}
}
