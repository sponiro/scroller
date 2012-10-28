package rob.scroller.entity;

import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.ScrollerGameContext;
import rob.scroller.map.BulletPrototype;

public class Enemy extends Character
{
	private BulletPrototype bulletPrototype;

	public Enemy(ScrollerGameContext context, Vector2f position)
	{
		super(context, position);
	}

	@Override
	protected Body createBody(Vector2f position)
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
		fixtureDef.filter.categoryBits = 2;
		fixtureDef.filter.maskBits = 4 | 8;

		Body body = context.getWorld().createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);

		return body;
	}

	@Override
	public void beforeWorldStep()
	{
		super.beforeWorldStep();

		Random random = new Random();
		if (random.nextFloat() < 0.1)
		{
			Bullet enemyBullet = context.getWorldFactory().createEnemyBullet(getPosition(), bulletPrototype);
			enemyBullet.setVelocity(new Vector2f(0, -5));
		}
	}

	@Override
	public void isHitBy(Entity entity)
	{
		if (entity instanceof Border)
		{
			markForRemoval();
		} else
		{
			super.isHitBy(entity);
		}
	}

	public BulletPrototype getBulletPrototype()
	{
		return bulletPrototype;
	}

	public void setBulletPrototype(BulletPrototype bulletPrototype)
	{
		this.bulletPrototype = bulletPrototype;
	}
}
