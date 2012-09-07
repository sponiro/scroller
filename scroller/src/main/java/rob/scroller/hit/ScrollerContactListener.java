package rob.scroller.hit;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import rob.scroller.entity.Bullet;
import rob.scroller.entity.Entity;

import com.google.inject.Inject;

public final class ScrollerContactListener implements ContactListener
{
	private final IBulletHitListener hitListener;

	@Inject
	public ScrollerContactListener(IBulletHitListener bulletHitListener)
	{
		this.hitListener = bulletHitListener;
	}

	public void preSolve(Contact contact, Manifold oldManifold)
	{

	}

	public void postSolve(Contact contact, ContactImpulse impulse)
	{

	}

	public void endContact(Contact contact)
	{

	}

	public void beginContact(Contact contact)
	{
		if (contact.getFixtureA().getBody().getUserData() instanceof Bullet)
		{
			if (contact.getFixtureB().getBody().getUserData() instanceof Entity)
			{
				hitListener.hit((Entity) contact.getFixtureB().getBody().getUserData(), (Bullet) contact
						.getFixtureA().getBody().getUserData());
			} else
			{
				Bullet bullet = (Bullet) contact.getFixtureA().getBody().getUserData();
				bullet.markForRemoval();
			}
		}

		if (contact.getFixtureB().getBody().getUserData() instanceof Bullet)
		{
			if (contact.getFixtureA().getBody().getUserData() instanceof Entity)
			{
				hitListener.hit((Entity) contact.getFixtureA().getBody().getUserData(), (Bullet) contact
						.getFixtureB().getBody().getUserData());
			} else
			{
				Bullet bullet = (Bullet) contact.getFixtureB().getBody().getUserData();
				bullet.markForRemoval();
			}
		}
	}
}