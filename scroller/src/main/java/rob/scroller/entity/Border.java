package rob.scroller.entity;

import org.jbox2d.dynamics.Body;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.ScrollerGameContext;

public class Border extends Entity
{

	public Border(ScrollerGameContext context, Vector2f position)
	{
		super(context, position);
	}

	@Override
	protected Body createBody(Vector2f position)
	{
		return null;
	}
}
