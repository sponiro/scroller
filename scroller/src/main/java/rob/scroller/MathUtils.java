package rob.scroller;

public class MathUtils
{
	public static int clamp(int x, int min, int max)
	{
		return Math.max(Math.min(x, max), min);
	}

	public static float clamp(float x, float min, float max)
	{
		return Math.max(Math.min(x, max), min);
	}
}
