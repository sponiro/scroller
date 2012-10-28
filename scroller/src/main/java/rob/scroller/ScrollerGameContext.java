package rob.scroller;

import java.awt.Font;
import java.io.IOException;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import rob.scroller.map.GameMap;
import rob.scroller.map.GameMapBuilder;
import rob.scroller.stream.MapArchive;

import com.google.inject.Inject;

public class ScrollerGameContext
{
	private static float WORLD_TIMESTEP = 1f / 60;

	private int displayWidth;
	private int displayHeight;

	private long nowInMilliseconds;
	private Vector2f mouseAim;

	private World world;
	private WorldFactory worldFactory;
	private WorldEntities worldEntities;

	private UnicodeFont textFont;
	private IRenderer renderer;

	private GameMap gameMap;

	@Inject
	public ScrollerGameContext(ContactListener contactListener)
	{
		world = new World(new Vec2(0, 0), true);
		world.setContactListener(contactListener);
	}

	@SuppressWarnings("unchecked")
	public void loadAndInit(MapArchive mapArchive) throws IOException, SlickException
	{
		loadGameMap(mapArchive);

		textFont = new UnicodeFont(new Font("Times New Roman", Font.PLAIN, 18));
		textFont.addAsciiGlyphs();
		textFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		textFont.loadGlyphs();
	}

	private void loadGameMap(MapArchive mapArchive) throws IOException
	{
		GameMapBuilder gameMapBuilder = new GameMapBuilder();
		gameMap = gameMapBuilder.load(mapArchive);
	}

	public World getWorld()
	{
		return world;
	}

	/**
	 * @return world simulation time step measure in fractions of a second
	 */
	public float getWorldTimestep()
	{
		return WORLD_TIMESTEP;
	}

	public int getDisplayWidth()
	{
		return displayWidth;
	}

	public void setDisplayWidth(int displayWidth)
	{
		this.displayWidth = displayWidth;
	}

	public int getDisplayHeight()
	{
		return displayHeight;
	}

	public void setDisplayHeight(int displayHeight)
	{
		this.displayHeight = displayHeight;
	}

	public UnicodeFont getTextFont()
	{
		return textFont;
	}

	/**
	 * Screen coordinates for the optimal player position
	 * 
	 * @return
	 */
	public Vector2f getScreenCenter()
	{
		Vector2f center = new Vector2f();
		center.setX(getDisplayWidth() / 2);
		center.setY(getDisplayHeight() / 2);
		return center;
	}

	public void setNowInMilliseconds(long nowInMilliseconds)
	{
		this.nowInMilliseconds = nowInMilliseconds;
	}

	public long getNowInMilliseconds()
	{
		return nowInMilliseconds;
	}

	public WorldFactory getWorldFactory()
	{
		return worldFactory;
	}

	@Inject
	public void setWorldFactory(WorldFactory worldFactory)
	{
		this.worldFactory = worldFactory;
	}

	public WorldEntities getWorldEntities()
	{
		return worldEntities;
	}

	@Inject
	public void setWorldEntities(WorldEntities worldEntities)
	{
		this.worldEntities = worldEntities;
	}

	public IRenderer getRenderer()
	{
		return renderer;
	}

	@Inject
	public void setRenderer(IRenderer renderer)
	{
		this.renderer = renderer;
	}

	public void setMouseAim(Vector2f mouseAim)
	{
		this.mouseAim = mouseAim;
	}

	public Vector2f getMouseAim()
	{
		return mouseAim;
	}

	public GameMap getGameMap()
	{
		return gameMap;
	}
}
