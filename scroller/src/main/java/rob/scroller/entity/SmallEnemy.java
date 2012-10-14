package rob.scroller.entity;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.ScrollerGameContext;

public class SmallEnemy extends Enemy
{
	public SmallEnemy(ScrollerGameContext context, Vector2f position)
	{
		super(context, position);
	}

	@Override
	protected Body createBody(Vector2f position)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position = new Vec2(position.x, position.y);

		PolygonShape box = new PolygonShape();

		Vec2 v1 = new Vec2(-1f / 2, 1f / 2);
		Vec2 v2 = new Vec2(0f, 0f);
		Vec2 v3 = new Vec2(1f / 2, 1f / 2);

		box.set(new Vec2[] { v1, v2, v3 }, 3);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
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
}
