package rob.scroller;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;

public class VectorConverter implements IVectorConverter
{
	private static final int PIXEL_PER_METER = 64;
	private static final float PIXEL_PER_METER_INVERSE = 1f / 64;

	public Vector2f convertToPixel(Vec2 vector)
	{
		Vector2f result = new Vector2f();
		result.x = PIXEL_PER_METER * vector.x;
		result.y = -PIXEL_PER_METER * vector.y;
		return result;
	}

	public Vec2 convertToMeter(Vector2f vector)
	{
		Vec2 result = new Vec2();
		result.x = PIXEL_PER_METER_INVERSE * vector.x;
		result.y = -PIXEL_PER_METER_INVERSE * vector.y;
		return result;
	}
}
