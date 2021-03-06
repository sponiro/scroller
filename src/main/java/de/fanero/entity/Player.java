package de.fanero.entity;

import com.google.inject.Inject;
import de.fanero.WorldFactory;
import de.fanero.WorldStepCounter;
import de.fanero.map.BulletPrototype;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.util.vector.Vector2f;

public class Player extends Character {

    private static final float MAX_SPEED = 4;

    private boolean shooting;
    private long lastShootTime;

    private BulletPrototype bulletPrototype;
    private WorldFactory worldFactory;
    private WorldStepCounter worldStepCounter;

    @Inject
    public Player(WorldFactory worldFactory, WorldStepCounter worldStepCounter) {
        this.worldFactory = worldFactory;
        this.worldStepCounter = worldStepCounter;

        setLife(100);
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
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.filter.maskBits = 4 | 8;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);
        setBody(body);

        return body;
    }

    public void moveDown() {
        Vector2f velocity = getVelocity();
        velocity.setY(-MAX_SPEED);
        setVelocity(velocity);

        normalizeVelocity();
    }

    public void moveUp() {
        Vector2f velocity = getVelocity();
        velocity.setY(MAX_SPEED);
        setVelocity(velocity);

        normalizeVelocity();
    }

    public void stopMoveVertical() {
        Vector2f velocity = getVelocity();
        velocity.setY(0);
        setVelocity(velocity);

        normalizeVelocity();
    }

    public void moveRight() {
        Vector2f velocity = getVelocity();
        velocity.setX(MAX_SPEED);
        setVelocity(velocity);

        normalizeVelocity();
    }

    public void moveLeft() {
        Vector2f velocity = getVelocity();
        velocity.setX(-MAX_SPEED);
        setVelocity(velocity);

        normalizeVelocity();
    }

    public void stopMoveHorizontal() {
        Vector2f velocity = getVelocity();
        velocity.setX(0);
        setVelocity(velocity);

        normalizeVelocity();
    }

    @Override
    public void beforeWorldStep() {
        super.beforeWorldStep();

        // shoot(context.getMouseAim());
        shoot(new Vector2f(0, 1));
    }

    private void normalizeVelocity() {
        setVelocity(getNormalizedVelocity());
    }

    private Vector2f getNormalizedVelocity() {
        Vector2f velocity = getVelocity();

        if (velocity.lengthSquared() >= 0.1) {
            velocity.scale(MAX_SPEED / velocity.length());
        } else {
            velocity.setX(0);
            velocity.setY(0);
        }

        return velocity;
    }

    private Vector2f getNormalizedBulletVector(Vector2f bulletVector) {
        Vector2f bulletVectorCopy = new Vector2f(bulletVector);

        if (bulletVectorCopy.lengthSquared() >= 0.1) {
            bulletVectorCopy.scale(MAX_SPEED * 3 / bulletVectorCopy.length());
        }

        return bulletVectorCopy;
    }

    public void startShooting() {
        this.shooting = true;
    }

    public void stopShooting() {
        this.shooting = false;
    }

    public boolean isShooting() {
        return shooting;
    }

    @Override
    public void isHitBy(Entity entity) {
        System.out.println("OMG, I'm hit!");
    }

    private boolean shootingAndBulletReady() {
        return isShooting() && worldStepCounter.getWorldTime() - getLastShootTime() >= getBulletIntervall();
    }

    private float getBulletIntervall() {
        return 5 * worldStepCounter.getWorldStep();
    }

    private long getLastShootTime() {
        return lastShootTime;
    }

    private Bullet shoot(Vector2f bulletVector) {

        Bullet bullet = null;

        if (shootingAndBulletReady()) {
            bullet = worldFactory.createBullet(getCenterPosition(), bulletPrototype);
            bullet.setVelocity(getNormalizedBulletVector(bulletVector));

            lastShootTime = worldStepCounter.getWorldTime();
        }

        return bullet;
    }

    public BulletPrototype getBulletPrototype() {
        return bulletPrototype;
    }

    public void setBulletPrototype(BulletPrototype bulletPrototype) {
        this.bulletPrototype = bulletPrototype;
    }
}
