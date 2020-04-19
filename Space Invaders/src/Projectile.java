import java.awt.Rectangle;
import java.util.Timer;

public abstract class Projectile {
	protected Rectangle projectileHitbox;
	protected Timer t;
	protected int width;
	protected int height;
	protected int speed;
	protected boolean active;
	public Projectile(int width, int height, int speed) {
		projectileHitbox = new Rectangle(0,0,0,0);
		this.width = width;
		this.height = height;
		this.speed = speed;
		active = false;
	}
	public Rectangle getProjectileHitbox() {
		return projectileHitbox;
	}
	protected boolean isActive() {
		return active;
	}
	public abstract void fireShot(int x, int y);
	public void stopProjectile() {
		if(active) {
			t.cancel();
			t.purge();
			active = false;
			projectileHitbox = new Rectangle(0,0,0,0);
		}
	}
}
