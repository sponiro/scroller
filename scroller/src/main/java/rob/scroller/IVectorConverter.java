package rob.scroller;

import org.lwjgl.util.vector.Vector2f;

public interface IVectorConverter
{
	Vector2f convertToPixel(Vector2f vector);
}