package de.fanero;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.fanero.entity.*;
import de.fanero.map.BulletPrototype;
import de.fanero.map.EnemyPrototype;
import de.fanero.map.PlayerPrototype;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

public class WorldFactory {

    private final ScrollerGameContext context;
    private Provider<Player> playerProvider;
    private Provider<Enemy> enemyProvider;
    private WorldEntities worldEntities;

    @Inject
    public WorldFactory(ScrollerGameContext context) {
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

    public Player createPlayer(Vector2f position, PlayerPrototype prototype) {

        Player player = playerProvider.get();
        player.createBody(context.getWorld(), position);
        player.setTexture(prototype.getTexture());
        player.setBulletPrototype(prototype.getBulletPrototypes().get(0));

        worldEntities.setPlayer(player);

        return player;
    }

    public Enemy createEnemy(Vector2f position, EnemyPrototype prototype) {
        Enemy enemy = enemyProvider.get();
        enemy.createBody(context.getWorld(), position);
        enemy.setTexture(prototype.getTexture());
        enemy.setBulletPrototype(prototype.getBulletPrototypes().get(0));
        enemy.setWidth(1);
        enemy.setHeight(1);

        worldEntities.addEnemy(enemy);

        return enemy;
    }

    public Bullet createBullet(Vector2f position, BulletPrototype prototype) {
        Bullet bullet = new Bullet();
        bullet.createBody(context.getWorld(), position);
        bullet.setTexture(prototype.getTexture());
        bullet.setWidth(.25f);
        bullet.setHeight(.25f);

        worldEntities.addBullet(bullet);

        return bullet;
    }

    public Bullet createEnemyBullet(Vector2f position, BulletPrototype prototype) {
        EnemyBullet bullet = new EnemyBullet();
        bullet.createBody(context.getWorld(), position);
        bullet.setTexture(prototype.getTexture());
        bullet.setWidth(.25f);
        bullet.setHeight(.25f);

        worldEntities.addBullet(bullet);

        return bullet;
    }

    public Dungeon createDungeon(int columns, int rows) {
        Dungeon dungeon = new Dungeon(context, columns, rows);

        createDungeonBorder(dungeon);

        worldEntities.setDungeon(dungeon);

        return dungeon;
    }

    private void createDungeonBorder(float width, float height, float distance, float borderWidth) {
        Vec2 v1 = new Vec2();
        Vec2 v2 = new Vec2(width, height);

        float halfBorderWidth = borderWidth / 2;
        float halfheight = Math.abs(v1.y - v2.y) / 2 + distance + borderWidth;
        float halfwidth = Math.abs(v1.x - v2.x) / 2 + distance + borderWidth;

        Vec2 vRight = new Vec2();
        vRight.x = Math.max(v1.x, v2.x) + halfBorderWidth + distance;
        vRight.y = (v1.y + v2.y) / 2;

        Vec2 vLeft = new Vec2();
        vLeft.x = Math.min(v1.x, v2.x) - halfBorderWidth - distance;
        vLeft.y = vRight.y;

        Vec2 vTop = new Vec2();
        vTop.x = (v1.x + v2.x) / 2;
        vTop.y = Math.max(v1.y, v2.y) + halfBorderWidth + distance * 4;

        Vec2 vBottom = new Vec2();
        vBottom.x = vTop.x;
        vBottom.y = Math.min(v1.y, v2.y) - halfBorderWidth - distance;

        createBorder(vRight, halfBorderWidth, halfheight);
        createBorder(vLeft, halfBorderWidth, halfheight);
        createBorder(vTop, halfwidth, halfBorderWidth);
        createBorder(vBottom, halfwidth, halfBorderWidth);
    }

    private void createDungeonBorder(Dungeon dungeon) {
        createDungeonBorder(dungeon.getColumns(), dungeon.getRows(), 1, 1);
    }

    private Body createBorder(Vec2 position, float halfwidth, float halfheight) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfwidth, halfheight);

        Body body = context.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = 8;
        fixtureDef.filter.maskBits = 0xffff;

        body.createFixture(fixtureDef);
        body.setUserData(new Border());

        return body;
    }
}
