package rob.scroller.hit;

import rob.scroller.entity.Bullet;
import rob.scroller.entity.Entity;

public interface IBulletHitListener
{
	void hit(Entity entity, Bullet bullet);
}
