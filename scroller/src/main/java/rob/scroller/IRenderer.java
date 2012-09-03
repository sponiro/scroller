package rob.scroller;

import org.newdawn.slick.opengl.Texture;

public interface IRenderer
{
	public abstract void fillRectangle(int x, int y, int width, int height, Texture texture);

	public abstract void blit(int x, int y, Texture texture);

}
