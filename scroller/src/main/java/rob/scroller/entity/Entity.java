package rob.scroller.entity;

import org.jbox2d.dynamics.Body;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import rob.scroller.ISimulationAction;
import rob.scroller.IVectorConverter;
import rob.scroller.ScrollerGameContext;
import rob.scroller.VectorUtils;

/**
 * Graphical entity with a position, velocity and texture
 * 
 */
public class Entity implements ISimulationAction
{
	private final ScrollerGameContext context;
	
	private Texture texture;
	private float width;
	private float height;
	
	private boolean markedForRemoval;
	
	private Body body;

	public Entity(ScrollerGameContext context)
	{
		this.context = context;
		this.markedForRemoval = false;
	}

	public Vector2f getVelocity()
	{
		return getVectorConverter().convertToPixel(body.getLinearVelocity());
	}

	protected IVectorConverter getVectorConverter()
	{
		return getContext().getVectorConverter();
	}

	public void setVelocity(Vector2f velocity)
	{
		body.setLinearVelocity(getVectorConverter().convertToMeter(velocity));
	}

	public Vector2f getPosition()
	{
		Vector2f position = new Vector2f();
		position.x = getCenterPosition().x - (float) texture.getImageWidth() / 2;
		position.y = getCenterPosition().y - (float) texture.getImageHeight() / 2;
		return position;
	}

	public void setPosition(Vector2f position)
	{
		Vector2f newPosition = new Vector2f();
		newPosition.x = getCenterPosition().x + (float) texture.getImageWidth() / 2;
		newPosition.y = getCenterPosition().y + (float) texture.getImageHeight() / 2;
		body.setTransform(getVectorConverter().convertToMeter(newPosition), 0);
	}

	public Vector2f getCenterPosition()
	{
		return getVectorConverter().convertToPixel(body.getPosition());
	}

	public void setCenterPosition(Vector2f center)
	{
		body.setTransform(getVectorConverter().convertToMeter(center), 0);
	}

	public void render(Vector2f origin)
	{
		Vector2f screenCoordinates = VectorUtils.getScreenCoordinates(origin, getPosition());

		context.getRenderer().blit((int) screenCoordinates.getX(), (int) screenCoordinates.getY(), getTexture());
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
	public void simulateStep()
	{
		body.setLinearVelocity(getVectorConverter().convertToMeter(getVelocity()));
	}

	public Body getBody()
	{
		return body;
	}

	public void setBody(Body body)
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
