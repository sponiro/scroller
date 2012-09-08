package rob.scroller.entity;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.ScrollerGameContext;

public class Player extends Character
{
	private static final float MAX_SPEED = 2;

	private boolean shooting;
	private long lastShootTime;

	private Vector2f velocity;

	public Player(ScrollerGameContext context, Vector2f position)
	{
		super(context, position);

		this.velocity = new Vector2f();
	}

	@Override
	protected void createBody(Vector2f position)
	{
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
		fixtureDef.filter.maskBits = 8;

		Body body = context.getWorld().createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);

		setBody(body);
	}

	public void moveDown()
	{
		velocity.setY(-MAX_SPEED);
		setVelocity(getNormalizedVelocity());
	}

	public void moveUp()
	{
		velocity.setY(MAX_SPEED);
		setVelocity(getNormalizedVelocity());
	}

	public void stopMoveVertical()
	{
		velocity.setY(0);
		setVelocity(getNormalizedVelocity());
	}

	public void moveRight()
	{
		velocity.setX(MAX_SPEED);
		setVelocity(getNormalizedVelocity());
	}

	public void moveLeft()
	{
		velocity.setX(-MAX_SPEED);
		setVelocity(getNormalizedVelocity());
	}

	public void stopMoveHorizontal()
	{
		velocity.setX(0);
		setVelocity(getNormalizedVelocity());
	}

	private Vector2f getNormalizedVelocity()
	{
		Vector2f velocity = new Vector2f(this.velocity);

		if (velocity.lengthSquared() > 1)
		{
			velocity.scale(MAX_SPEED / velocity.length());
		}

		return velocity;
	}

	@Override
	public Vector2f getVelocity()
	{
		Vector2f velocity = new Vector2f(super.getVelocity());

		if (velocity.lengthSquared() > 1)
		{
			velocity.scale(MAX_SPEED / velocity.length());
		}

		return velocity;
	}

	@Override
	public void beforeWorldStep()
	{
		super.beforeWorldStep();

		shoot(context.getMouseAim());
	}

	private Vector2f getNormalizedBulletVector(Vector2f bulletVector)
	{
		Vector2f bulletVectorCopy = new Vector2f(bulletVector);

		if (bulletVectorCopy.lengthSquared() > 1)
		{
			bulletVectorCopy.scale(MAX_SPEED * 4 / bulletVectorCopy.length());
		}

		return bulletVectorCopy;
	}

	public void startShooting()
	{
		this.shooting = true;
	}

	public void stopShooting()
	{
		this.shooting = false;
	}

	public boolean isShooting()
	{
		return shooting;
	}

	public boolean nextBulletReady()
	{
		return isShooting() && context.getNowInMilliseconds() - getLastShootTime() >= getBulletIntervall();
	}

	private float getBulletIntervall()
	{
		return 5 * context.getWorldTimestep() * 1000;
	}

	public long getLastShootTime()
	{
		return lastShootTime;
	}

	public Bullet shoot(Vector2f bulletVector)
	{
		Bullet bullet = null;

		if (nextBulletReady())
		{
			bullet = context.getWorldFactory().createBullet(getCenterPosition());
			bullet.setVelocity(getNormalizedBulletVector(bulletVector));

			lastShootTime = context.getNowInMilliseconds();
		}

		return bullet;
	}
}
