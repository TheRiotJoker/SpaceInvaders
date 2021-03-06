import java.awt.Rectangle;
import java.util.TimerTask;

import java.util.Timer;

public class PlayerProjectile extends Projectile {

	public PlayerProjectile(int width, int height, int speed) {
		super(width, height, speed);
	}
	@Override
	public void fireShot(int x, int y) {
		projectileHitbox = new Rectangle(x,y,width,height);
		t = new Timer();
		active = true;
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if(projectileHitbox.y+projectileHitbox.getHeight() > 45) {
					projectileHitbox.y--; 
				} else {
					stopProjectile();
				}
				
			}
			
		}, 0, speed);
	}

}
