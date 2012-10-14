package rob.scroller;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Renderer implements IRenderer
{

	@Override
	public void blitClamped(float x, float y, float width, float height, Texture texture)
	{
		texture.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

		blit(x, y, width, height);
	}

	@Override
	public void blitRepeated(float x, float y, float width, float height, Texture texture)
	{
		texture.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

		blit(x, y, width, height);
	}

	private void blit(float x, float y, float width, float height)
	{
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + width, y);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + width, y + height);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + height);

		GL11.glEnd();
	}
}
