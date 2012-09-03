package rob.scroller;

import rob.scroller.entity.Bullet;
import rob.scroller.entity.Entity;

public final class BulletHitListener implements IBulletHitListener
{
	@Override
	public void hit(Entity entity, Bullet bullet)
	{
		// a bullet can hit more than one target at once
		// so we check if it already has been processed or not
		if (!bullet.isMarkedForRemoval())
		{
			entity.isHitBy(bullet);
		}
	}
}