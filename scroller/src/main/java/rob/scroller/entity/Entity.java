package rob.scroller.entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import rob.scroller.IRenderer;
import rob.scroller.ISimulationAction;
import rob.scroller.ScrollerGameContext;

/**
 * Graphical entity with a position, velocity and texture
 * 
 */
public class Entity implements ISimulationAction
{
	protected final ScrollerGameContext context;

	private Texture texture;
	private float width;
	private float height;

	private Vector2f velocity;

	private boolean markedForRemoval;

	private Body body;

	public Entity(ScrollerGameContext context, Vector2f position)
	{
		this.context = context;
		this.markedForRemoval = false;
		this.velocity = new Vector2f();
		this.width = 1;
		this.height = 1;

		createBody(position);
	}

	protected void createBody(Vector2f position)
	{

	}

	public Vector2f getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector2f velocity)
	{
		this.velocity = velocity;
	}

	protected Vector2f getBodyVelocity()
	{
		return new Vector2f(body.getLinearVelocity().x, body.getLinearVelocity().y);
	}

	protected void setBodyVelocity(Vector2f velocity)
	{
		body.setLinearVelocity(new Vec2(velocity.x, velocity.y));
	}

	public Vector2f getPosition()
	{
		Vector2f position = new Vector2f();
		position.x = getCenterPosition().x - width / 2;
		position.y = getCenterPosition().y - height / 2;
		return position;
	}

	// public void setPosition(Vector2f position)
	// {
	// Vector2f newPosition = new Vector2f();
	// newPosition.x = getCenterPosition().x + (float) texture.getImageWidth() /
	// 2;
	// newPosition.y = getCenterPosition().y + (float) texture.getImageHeight()
	// / 2;
	// body.setTransform(getVectorConverter().convertToMeter(newPosition), 0);
	// }

	public Vector2f getCenterPosition()
	{
		return new Vector2f(body.getPosition().x, body.getPosition().y);
	}

	// public void setCenterPosition(Vector2f center)
	// {
	// body.setTransform(new Vec2(center.x, center.y), 0);
	// }

	public void render(IRenderer renderer)
	{
		Vector2f p = getPosition();

		renderer.blit(p.x, p.y, width, height, getTexture());
	}

	public void isHitBy(Bullet bullet)
	{
		markForRemoval();
		bullet.markForRemoval();
	}

	public boolean isMarkedForRemoval()
	{
		return markedForRemoval;
	}

	public void markForRemoval()
	{
		this.markedForRemoval = true;
	}

	public ScrollerGameContext getContext()
	{
		return context;
	}

	public void destroy()
	{
		context.getWorld().destroyBody(body);
	}

	@Override
	public void beforeWorldStep()
	{
		setBodyVelocity(velocity);
	}

	public void afterWorldStep()
	{
		velocity = getBodyVelocity();
	}

	public Body getBody()
	{
		return body;
	}

	protected void setBody(Body body)
	{
		this.body = body;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
}
