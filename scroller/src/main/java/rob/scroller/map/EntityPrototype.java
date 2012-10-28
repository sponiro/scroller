package rob.scroller.map;

import org.newdawn.slick.opengl.Texture;

import com.google.common.base.Preconditions;

public class EntityPrototype
{
	private String name;
	private Texture texture;

	public EntityPrototype()
	{
		name = "";
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		Preconditions.checkNotNull(name);

		this.name = name;
	}
}
