package de.fanero;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import de.fanero.entity.Enemy;
import de.fanero.entity.Player;
import de.fanero.hit.ScrollerContactListener;
import org.jbox2d.callbacks.ContactListener;

/**
 * Guice module
 */
public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ScrollerGameContext.class).in(Scopes.SINGLETON);
        bind(WorldEntities.class).in(Scopes.SINGLETON);
        bind(WorldFactory.class).in(Scopes.SINGLETON);

        bind(IRenderer.class).to(Renderer.class).in(Scopes.SINGLETON);;

        bind(ContactListener.class).to(ScrollerContactListener.class).in(Scopes.SINGLETON);;

        bind(Player.class);
        bind(Enemy.class);
    }
}
