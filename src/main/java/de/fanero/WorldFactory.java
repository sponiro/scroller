package de.fanero;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.fanero.entity.Bullet;
import de.fanero.entity.Enemy;
import de.fanero.entity.EnemyBullet;
import de.fanero.entity.Player;
import de.fanero.map.BulletPrototype;
import de.fanero.map.EnemyPrototype;
import de.fanero.map.PlayerPrototype;
import org.lwjgl.util.vector.Vector2f;

public class WorldFactory {

    private ScrollerGameContext context;
    private Provider<Player> playerProvider;
    private Provider<Enemy> enemyProvider;
    private Provider<Dungeon> dungeonProvider;
    private Provider<Bullet> bulletProvider;
    private Provider<EnemyBullet> enemyBulletProvider;
    private WorldStepCounter worldStepCounter;
    private WorldEntities worldEntities;

    public Player createPlayer(Vector2f position, PlayerPrototype prototype) {

        Player player = playerProvider.get();
        player.createBody(worldStepCounter.getWorld(), position);
        player.setTexture(prototype.getTexture());
        player.setBulletPrototype(prototype.getBulletPrototypes().get(0));

        worldEntities.setPlayer(player);

        return player;
    }

    public Enemy createEnemy(Vector2f position, EnemyPrototype prototype) {

        Enemy enemy = enemyProvider.get();
        enemy.createBody(worldStepCounter.getWorld(), position);
        enemy.setTexture(prototype.getTexture());
        enemy.setBulletPrototype(prototype.getBulletPrototypes().get(0));
        enemy.setWidth(1);
        enemy.setHeight(1);

        worldEntities.addEnemy(enemy);

        return enemy;
    }

    public Bullet createBullet(Vector2f position, BulletPrototype prototype) {

        Bullet bullet = bulletProvider.get();
        bullet.createBody(worldStepCounter.getWorld(), position);
        bullet.setTexture(prototype.getTexture());
        bullet.setWidth(.25f);
        bullet.setHeight(.25f);

        worldEntities.addBullet(bullet);

        return bullet;
    }

    public Bullet createEnemyBullet(Vector2f position, BulletPrototype prototype) {

        EnemyBullet bullet = new EnemyBullet();
        bullet.createBody(worldStepCounter.getWorld(), position);
        bullet.setTexture(prototype.getTexture());
        bullet.setWidth(.25f);
        bullet.setHeight(.25f);

        worldEntities.addBullet(bullet);

        return bullet;
    }

    public Dungeon createDungeon(int columns, int rows) {

        Dungeon dungeon = dungeonProvider.get();
        dungeon.init(columns, rows);

        worldEntities.setDungeon(dungeon);

        return dungeon;
    }

    @Inject
    public void setContext(ScrollerGameContext context) {
        this.context = context;
    }

    @Inject
    public void setPlayerProvider(Provider<Player> playerProvider) {
        this.playerProvider = playerProvider;
    }

    @Inject
    public void setEnemyProvider(Provider<Enemy> enemyProvider) {
        this.enemyProvider = enemyProvider;
    }

    @Inject
    public void setWorldEntities(WorldEntities worldEntities) {
        this.worldEntities = worldEntities;
    }

    @Inject
    public void setDungeonProvider(Provider<Dungeon> dungeonProvider) {
        this.dungeonProvider = dungeonProvider;
    }

    @Inject
    public void setBulletProvider(Provider<Bullet> bulletProvider) {
        this.bulletProvider = bulletProvider;
    }

    @Inject
    public void setEnemyBulletProvider(Provider<EnemyBullet> enemyBulletProvider) {
        this.enemyBulletProvider = enemyBulletProvider;
    }

    @Inject
    public void setWorldStepCounter(WorldStepCounter worldStepCounter) {
        this.worldStepCounter = worldStepCounter;
    }
}
