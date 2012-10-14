package rob.scroller;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import rob.scroller.entity.Enemy;
import rob.scroller.entity.Entity;
import rob.scroller.entity.Player;
import rob.scroller.input.PlayerInput;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ScrollerGame
{
	private final float TILE_SIZE = 50;

	private ScrollerGameContext context;

	/** frames per second */
	private int fps;

	/** last fps time */
	private long lastFPSTime;
	private int lastFPS;

	private boolean wantsExit;

	private Random random = new Random();
	private FloatBuffer modelBuffer;
	private FloatBuffer projectionBuffer;
	private IntBuffer viewBuffer;

	private WorldStepCounter worldStepCounter;

	private PlayerInput playerInput = new PlayerInput();

	public void start() throws SlickException
	{
		context.setDisplayWidth(800);
		context.setDisplayHeight(600);
		// context.setDisplayWidth(1000);
		// context.setDisplayHeight(750);

		try
		{
			Display.setDisplayMode(new DisplayMode(context.getDisplayWidth(), context.getDisplayHeight()));
			Display.setTitle("Scroller");
			Display.create();

			Keyboard.enableRepeatEvents(true);

			context.loadAndInit();

		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);

		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		initGame();
		initGL();

		worldStepCounter = new WorldStepCounter(context.getWorldTimestep());
		worldStepCounter.reset(getTime());

		lastFPSTime = worldStepCounter.getWorldTime();
		context.setNowInMilliseconds(worldStepCounter.getWorldTime());

		wantsExit = false;

		while (!Display.isCloseRequested() && !wantsExit)
		{
			processInput();
			simulateWorld();
			renderObjects();

			// sleep to keep 60 fps
			Display.sync(60);
			Display.update();

			worldStepCounter.updateCurrentTime(getTime());
		}

		Display.destroy();
	}

	private void initGL()
	{
		// enable textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		modelBuffer = BufferUtils.createFloatBuffer(16);
		projectionBuffer = BufferUtils.createFloatBuffer(16);
		viewBuffer = BufferUtils.createIntBuffer(16);
	}

	private void initGame()
	{
		context.getWorldFactory().createDungeon(16, 12);
		context.getWorldFactory().createPlayer(new Vector2f(8f, 2));
		context.getWorldEntities().getDungeon().resetToStart();
	}

	private Vector2f randomPosition(Random random)
	{
		return new Vector2f(random.nextFloat() * 15 + .5f, 12.5f);
	}

	private void processInput()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				switch (Keyboard.getEventKey())
				{
				case Keyboard.KEY_DOWN:
					playerInput.pressDown();
					break;
				case Keyboard.KEY_UP:
					playerInput.pressUp();
					break;
				case Keyboard.KEY_RIGHT:
					playerInput.pressRight();
					break;
				case Keyboard.KEY_LEFT:
					playerInput.pressLeft();
					break;
				}
			} else
			{
				switch (Keyboard.getEventKey())
				{
				case Keyboard.KEY_DOWN:
					playerInput.releaseDown();
					break;
				case Keyboard.KEY_UP:
					playerInput.releaseUp();
					break;
				case Keyboard.KEY_RIGHT:
					playerInput.releaseRight();
					break;
				case Keyboard.KEY_LEFT:
					playerInput.releaseLeft();
					break;
				}
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_X)
			{
				if (Keyboard.getEventKeyState())
				{
					getPlayer().startShooting();
				} else
				{
					getPlayer().stopShooting();
				}
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
			{
				this.wantsExit = true;
			}
		}

		switch (playerInput.getVerticalDirection())
		{
		case UP:
			getPlayer().moveUp();
			break;
		case DOWN:
			getPlayer().moveDown();
			break;
		case NONE:
			getPlayer().stopMoveVertical();
			break;
		default:
			break;
		}

		switch (playerInput.getHorizontalDirection())
		{
		case LEFT:
			getPlayer().moveLeft();
			break;
		case RIGHT:
			getPlayer().moveRight();
			break;
		case NONE:
			getPlayer().stopMoveHorizontal();
		default:
			break;
		}
		
		while (Mouse.next())
		{
			if (Mouse.getEventButton() == 0)
			{
				if (Mouse.getEventButtonState())
				{
					getPlayer().startShooting();

				} else
				{
					getPlayer().stopShooting();
				}
			}
		}
	}

	private Player getPlayer()
	{
		return context.getWorldEntities().getPlayer();
	}

	private void simulateWorld()
	{
		// we use fixed timesteps for physics
		// physics simulation lags behind frame rendering
		// several simulation steps might be necessary between renders
		while (worldStepCounter.needsStep())
		{
			context.setMouseAim(getMouseAim());

			createEnemies();

			getWorldEntities().beforeWorldStep();

			worldStepCounter.step();
			context.setNowInMilliseconds(worldStepCounter.getWorldTime());

			context.getWorld().step(context.getWorldTimestep(), 8, 3);

			getWorldEntities().afterWorldStep();
		}

		removeDeadEntities();
	}

	private void createEnemies()
	{
		if (random.nextFloat() < 0.01f)
		{
			// Enemy enemy =
			// context.getWorldFactory().createEnemy(randomPosition(random));
			// enemy.setVelocity(new Vector2f(0f, -(random.nextFloat() * 1.3f +
			// .1f)));

			Enemy enemy = context.getWorldFactory().createSmallEnemy(randomPosition(random));
			enemy.setVelocity(new Vector2f(0f, -2));
		}
	}

	private void removeDeadEntities()
	{
		Iterator<Entity> it = getWorldEntities().getEntities().iterator();

		while (it.hasNext())
		{
			Entity entity = it.next();

			if (entity.isMarkedForRemoval())
			{
				entity.destroy();
				it.remove();
			}
		}
	}

	private WorldEntities getWorldEntities()
	{
		return context.getWorldEntities();
	}

	private Vector2f getMouseAim()
	{
		return Vector2f.sub(getMousePosition(), getPlayerScreenPosition(), null);
	}

	private Vector2f getMousePosition()
	{
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}

	private Vector2f getPlayerScreenPosition()
	{
		return project(getPlayer().getCenterPosition());
	}

	private Vector2f unproject(Vector2f vector)
	{
		FloatBuffer pos = BufferUtils.createFloatBuffer(3);

		boolean result = GLU.gluUnProject(vector.x, vector.y, 1, modelBuffer, projectionBuffer, viewBuffer, pos);

		if (result)
		{
			return new Vector2f(pos.get(0), pos.get(1));
		} else
		{
			return new Vector2f();
		}
	}

	/**
	 * Project a vector from world coordinates to screen coordinates.
	 * 
	 * @param vector
	 * @return
	 */
	private Vector2f project(Vector2f vector)
	{
		FloatBuffer pos = BufferUtils.createFloatBuffer(3);

		boolean result = GLU.gluProject(vector.x, vector.y, 1f, modelBuffer, projectionBuffer, viewBuffer, pos);

		if (result)
		{
			return new Vector2f(pos.get(0), pos.get(1));
		} else
		{
			return new Vector2f();
		}
	}

	private void renderObjects()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glPushMatrix();
		GL11.glScalef(TILE_SIZE, TILE_SIZE, 1);

		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelBuffer);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionBuffer);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewBuffer);

		getWorldEntities().render();

		GL11.glPopMatrix();

		updateFPS();
	}

	/**
	 * Calculate the FPS and draws it on screen
	 */
	private void updateFPS()
	{
		if (getTime() - lastFPSTime > 1000)
		{
			lastFPS = fps;
			fps = 0;
			lastFPSTime += 1000;
		}

		fps++;

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 600, 0);
		GL11.glScalef(1, -1, 1);

		context.getTextFont().drawString(0, 0, Long.toString(lastFPS) + " fps", Color.white);
		context.getTextFont().drawString(0, 20, Long.toString(context.getWorldEntities().getBullets().size()) + " bullets", Color.white);
		context.getTextFont().drawString(0, 40, Long.toString(context.getWorldEntities().getEnemies().size()) + " enemies", Color.white);

		GL11.glPopMatrix();
	}

	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds
	 */
	private long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Main entry point for scroller
	 * 
	 * @param argv
	 * @throws SlickException
	 */
	public static void main(String[] argv) throws SlickException
	{
		Injector injector = Guice.createInjector(new GameModule());

		ScrollerGame game = injector.getInstance(ScrollerGame.class);
		game.start();
	}

	@Inject
	public void setContext(ScrollerGameContext context)
	{
		this.context = context;
	}
}
