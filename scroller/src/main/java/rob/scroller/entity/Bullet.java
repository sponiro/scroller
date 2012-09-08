package rob.scroller.entity;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.ScrollerGameContext;

public class Bullet extends Entity
{
	private int damage;

	public Bullet(ScrollerGameContext context, Vector2f position)
	{
		super(context, position);

		this.damage = 1;
	}

	@Override
	protected void createBody(Vector2f position)
	{
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
		fixtureDef.filter.maskBits = 2 | 8;

		Body body = context.getWorld().createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);

		setBody(body);
	}
	
	public int getDamage()
	{
		return damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}
}
