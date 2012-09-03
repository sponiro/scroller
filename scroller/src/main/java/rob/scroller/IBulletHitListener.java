package rob.scroller;

import rob.scroller.entity.Bullet;
import rob.scroller.entity.Entity;

public interface IBulletHitListener
{
	public void hit(Entity entity, Bullet bullet);
}
