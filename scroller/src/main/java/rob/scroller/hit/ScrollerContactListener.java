package rob.scroller.hit;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import rob.scroller.entity.Border;
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
		Object userDataA = contact.getFixtureA().getBody().getUserData();
		Object userDataB = contact.getFixtureB().getBody().getUserData();

		beginUserDataContact(userDataA, userDataB);
		beginUserDataContact(userDataB, userDataA);
	}

	private void beginUserDataContact(Object userDataA, Object userDataB)
	{
		if (userDataA instanceof Bullet)
		{
			if (userDataB instanceof Entity)
			{
				hitListener.hit((Entity) userDataB, (Bullet) userDataA);
			} else
			{
				Bullet bullet = (Bullet) userDataA;
				bullet.markForRemoval();
			}
		} else if (userDataA instanceof Border)
		{
			if (userDataB instanceof Entity)
			{
				Entity entity = (Entity) userDataB;
				entity.markForRemoval();
			}
		}
	}
}