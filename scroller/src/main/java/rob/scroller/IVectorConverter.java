package rob.scroller;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;

public interface IVectorConverter
{
	Vec2 convertToMeter(Vector2f vector);

	Vector2f convertToPixel(Vec2 vector);

	Vector2f convertToPixel(Vector2f vector);
}