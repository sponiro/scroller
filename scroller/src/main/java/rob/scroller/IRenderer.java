package rob.scroller;

import org.newdawn.slick.opengl.Texture;

public interface IRenderer
{
	void blit(float x, float y, float width, float height, Texture texture);
}
