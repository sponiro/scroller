package rob.scroller;

import java.awt.Font;
import java.io.IOException;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.google.inject.Inject;

public class ScrollerGameContext
{
	private static float WORLD_TIMESTEP = 1f / 60;

	private int displayWidth;
	private int displayHeight;

	private Texture floorTexture;
	private Texture playerTexture;
	private Texture enemyTexture;
	private Texture emptyTexture;
	private Texture bulletTexture;

	private UnicodeFont textFont;

	private long nowInMilliseconds;

	private IVectorConverter vectorConverter;
	private IRenderer renderer;
	private WorldFactory worldFactory;
	private WorldEntities worldEntities;

	private World world;

	private Vector2f mouseAim;

	@Inject
	public ScrollerGameContext(ContactListener contactListener)
	{
		world = new World(new Vec2(0, 0), true);
		world.setContactListener(contactListener);
	}

	public void loadAndInit() throws IOException, SlickException
	{
		floorTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/floor.png"));
		playerTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/player2.png"));
		enemyTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/enemy.png"));
		emptyTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/empty.png"));
		bulletTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/bullet.png"));

		textFont = new UnicodeFont(new Font("Times New Roman", Font.PLAIN, 14));
		textFont.addAsciiGlyphs();
		textFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		textFont.loadGlyphs();

		initGL();
	}

	private void initGL()
	{
		// enable textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// GL11.glShadeModel(GL11.GL_SMOOTH);
		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// GL11.glClearDepth(1);

		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, displayWidth, displayHeight, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
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

	public Texture getFloorTexture()
	{
		return floorTexture;
	}

	public Texture getPlayerTexture()
	{
		return playerTexture;
	}

	public Texture getEnemyTexture()
	{
		return enemyTexture;
	}

	public Texture getEmptyTexture()
	{
		return emptyTexture;
	}

	public Texture getBulletTexture()
	{
		return bulletTexture;
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

	public IVectorConverter getVectorConverter()
	{
		return vectorConverter;
	}

	@Inject
	public void setVectorConverter(IVectorConverter vectorConverter)
	{
		this.vectorConverter = vectorConverter;
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
}
