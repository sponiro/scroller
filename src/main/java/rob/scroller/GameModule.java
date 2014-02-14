package rob.scroller;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.jbox2d.callbacks.ContactListener;
import rob.scroller.hit.ScrollerContactListener;

/**
 * Guice module
 */
public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IRenderer.class).to(Renderer.class);
        bind(WorldFactory.class);

        bind(ContactListener.class).to(ScrollerContactListener.class);

        bind(ScrollerGameContext.class).in(Singleton.class);
    }
}
