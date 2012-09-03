package rob.scroller;

import java.text.MessageFormat;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class Box2dTest
{

	private static final class MyContactListener implements ContactListener
	{
		public void preSolve(Contact contact, Manifold oldManifold)
		{
			// TODO Auto-generated method stub
			
		}

		public void postSolve(Contact contact, ContactImpulse impulse)
		{
			// TODO Auto-generated method stub
			
		}

		public void endContact(Contact contact)
		{
			// TODO Auto-generated method stub
			
		}

		public void beginContact(Contact contact)
		{
			System.out.println("Box2dTest.MyContactListener.beginContact()");
			printBody(contact.m_fixtureA.getBody());
			printBody(contact.m_fixtureB.getBody());
			contact.m_fixtureA.getBody().setLinearVelocity(new Vec2(0, 0));
			contact.m_fixtureB.getBody().setLinearVelocity(new Vec2(0, 10));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Vec2 gravity = new Vec2(0, 0);
		World world = new World(gravity, true);
		world.setContactListener(new MyContactListener());
		
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0, -10);

		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(50, 10);

		groundBody.createFixture(groundBox, 0);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(0, 4);
		bodyDef.linearVelocity = new Vec2(0, -10);
		Body body = world.createBody(bodyDef);
		
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(2, 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1;
//		fixtureDef.friction = 0.3f;
		fixtureDef.friction = 0;

		body.createFixture(fixtureDef);

		float timeStep = 1f / 60f;

		int velocityIterations = 6;
		int positionIterations = 2;

		for (int i = 0; i < 60; i++)
		{
			world.step(timeStep, velocityIterations, positionIterations);

			printBody(body);
		}
	}

	private static void printBody(Body body)
	{
		Vec2 position = body.getPosition();
		float angle = body.getAngle();
		System.out.println(MessageFormat.format("{0,number,00.00} {1,number,00.00} {2,number,00.00}", position.x, position.y, angle));
	}
}
