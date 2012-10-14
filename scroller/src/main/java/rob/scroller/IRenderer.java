package rob.scroller;

import org.newdawn.slick.opengl.Texture;

public interface IRenderer
{
	void blitClamped(float x, float y, float width, float height, Texture texture);

	void blitRepeated(float x, float y, float width, float height, Texture texture);
}
