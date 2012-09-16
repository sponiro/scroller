package rob.scroller;

import org.jbox2d.callbacks.ContactListener;

import rob.scroller.hit.BulletHitListener;
import rob.scroller.hit.IBulletHitListener;
import rob.scroller.hit.ScrollerContactListener;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Guice module
 * 
 */
public class GameModule extends AbstractModule
{

	@Override
	protected void configure()
	{
		bind(IRenderer.class).to(Renderer.class);
		bind(WorldFactory.class);
		
		bind(ContactListener.class).to(ScrollerContactListener.class);
		bind(IBulletHitListener.class).to(BulletHitListener.class);
		
		bind(ScrollerGameContext.class).in(Singleton.class);
	}
}
