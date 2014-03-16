package de.fanero;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.fanero.hit.ScrollerContactListener;
import de.fanero.input.IPlayerInput;
import de.fanero.input.PlayerInput;
import de.fanero.map.GameMapBuilder;
import de.fanero.map.IGameMapBuilder;
import de.fanero.stream.IMapLoader;
import de.fanero.stream.MapLoader;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Guice module
 */
public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ScrollerGameContext.class).in(Scopes.SINGLETON);
        bind(WorldEntities.class).in(Scopes.SINGLETON);
        bind(WorldFactory.class).in(Scopes.SINGLETON);

        bind(IRenderer.class).to(Renderer.class).in(Scopes.SINGLETON);

        bind(ContactListener.class).to(ScrollerContactListener.class).in(Scopes.SINGLETON);

        bind(IMapLoader.class).to(MapLoader.class);
        bind(IGameLoader.class).to(GameLoader.class);
        bind(IScrollerGame.class).to(ScrollerGame.class);
        bind(IGameMapBuilder.class).to(GameMapBuilder.class);

        bind(IPlayerInput.class).to(PlayerInput.class);

//        bind(Player.class);
//        bind(Enemy.class);
    }

    @Provides
    @Singleton
    public Config provideConfig() {
        Config config = ConfigFactory.load();
        return config;
    }

    @Provides
    @Singleton
    public WorldStepCounter provideWorldStepCounter(Config config, ContactListener contactListener) {

        World world = new World(new Vec2(0, 0), true);
        world.setContactListener(contactListener);

        double worldtimestep = config.getDouble("scroller.worldtimestep");
        WorldStepCounter worldStepCounter = new WorldStepCounter((float) worldtimestep, world);
        return worldStepCounter;
    }
}
