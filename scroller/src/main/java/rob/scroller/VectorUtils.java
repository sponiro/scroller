package rob.scroller;

import org.lwjgl.util.vector.Vector2f;

public class VectorUtils
{

	/**
	 * Gets the screen coordinates of a vector in world coordinates based on the
	 * origin.
	 * 
	 * @param origin
	 * @param vector
	 * @return
	 */
	public static Vector2f getScreenCoordinates(Vector2f origin, Vector2f vector)
	{
		return Vector2f.sub(vector, origin, null);
	}
}
