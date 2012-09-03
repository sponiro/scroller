package rob.scroller;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Renderer implements IRenderer
{
	@Override
	public void blit(int x, int y, Texture texture)
	{
		texture.bind();

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + texture.getTextureWidth(), y);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + texture.getTextureWidth(), y + texture.getTextureHeight());

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + texture.getTextureHeight());

		GL11.glEnd();
	}

	@Override
	public void fillRectangle(int x, int y, int width, int height, Texture texture)
	{
		for (int left = x; left < width; left += texture.getImageWidth())
		{
			for (int top = y; top < height; top += texture.getImageHeight())
			{
				blit(left, top, texture);
			}
		}
	}
}
