package de.fanero.entity;

import com.google.inject.Inject;
import de.fanero.WorldFactory;
import de.fanero.map.BulletPrototype;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.util.vector.Vector2f;

import java.util.Random;

public class Enemy extends Character {
    private BulletPrototype bulletPrototype;
    private WorldFactory worldFactory;

    @Inject
    public Enemy(WorldFactory worldFactory) {
        this.worldFactory = worldFactory;
    }

    @Override
    public Body createBody(World world, Vector2f position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(position.x, position.y);

        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(1f / 2, 1f / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 4 | 8;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);
        setBody(body);

        return body;
    }

    @Override
    public void beforeWorldStep() {
        super.beforeWorldStep();

        Random random = new Random();
        if (random.nextFloat() < 0.1) {
            Bullet bullet = worldFactory.createEnemyBullet(getCenterPosition(), bulletPrototype);
            bullet.setVelocity(new Vector2f(0, -5));
        }
    }

    @Override
    public void isHitBy(Entity entity) {
        if (entity instanceof Border) {
            markForRemoval();
        } else {
            super.isHitBy(entity);
        }
    }

    public BulletPrototype getBulletPrototype() {
        return bulletPrototype;
    }

    public void setBulletPrototype(BulletPrototype bulletPrototype) {
        this.bulletPrototype = bulletPrototype;
    }
}
