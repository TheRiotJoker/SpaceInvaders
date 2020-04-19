import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class EnemyProjectile extends Projectile{

	public EnemyProjectile(int width, int height, int speed) {
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
				if(projectileHitbox.y+projectileHitbox.getHeight() < 440) {
					projectileHitbox.y++;
				} else {
					stopProjectile();
				}
				
			}
			
		}, 0, speed);
		
	}

}
