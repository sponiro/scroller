package de.fanero.entity;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.util.vector.Vector2f;


public class EnemyBullet extends Bullet {
    @Override
    public Body createBody(World world, Vector2f position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(position.x, position.y);
        bodyDef.bullet = true;

        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = 1f / 8;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = 4;
        fixtureDef.filter.maskBits = 1 | 8;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);
        setBody(body);

        return body;
    }

    @Override
    public void isHitBy(Entity entity) {
        markForRemoval();
    }
}
