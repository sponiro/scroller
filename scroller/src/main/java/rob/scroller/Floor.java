package rob.scroller;

import org.newdawn.slick.opengl.Texture;

public class Floor
{
	private Texture texture;

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public Texture getTexture()
	{
		return texture;
	}
}
