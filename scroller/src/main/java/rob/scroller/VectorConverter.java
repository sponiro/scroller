package rob.scroller;

import org.lwjgl.util.vector.Vector2f;

public class VectorConverter implements IVectorConverter
{
	private static final int PIXEL_PER_METER = 64;

	@Override
	public Vector2f convertToPixel(Vector2f vector)
	{
		Vector2f result = new Vector2f();
		result.x = PIXEL_PER_METER * vector.x;
		result.y = PIXEL_PER_METER * vector.y;
		return result;
	}	
}
