package rob.scroller;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;

public interface IVectorConverter
{
	public Vec2 convertToMeter(Vector2f vector);

	public Vector2f convertToPixel(Vec2 vector);
}