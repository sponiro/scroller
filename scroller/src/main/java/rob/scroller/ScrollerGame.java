package rob.scroller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import rob.scroller.entity.Enemy;
import rob.scroller.entity.Entity;
import rob.scroller.entity.Player;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ScrollerGame
{
	private ScrollerGameContext context;

	/** frames per second */
	private int fps;

	/** last fps time */
	private long lastFPSTime;
	private int lastFPS;

	private long lastFrameTime;
	private long lastWorldTime;

	private boolean wantsExit;

	public void start() throws SlickException
	{
		context.setDisplayWidth(800);
		context.setDisplayHeight(600);
//		context.setDisplayWidth(1000);
//		context.setDisplayHeight(750);

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

		setFrameTime();
		lastFPSTime = getTime(); // call before loop to initialise fps timer
		lastWorldTime = lastFPSTime;

		initGame();

		wantsExit = false;

		while (!Display.isCloseRequested() && !wantsExit)
		{
			setFrameTime();

			processInput();
			simulateWorld();
			renderObjects();

			// sleep to keep 60 fps
			Display.sync(60);
			Display.update();
		}

		Display.destroy();
	}

	private void initGame()
	{
		context.getWorldFactory().createDungeon(13, 10);
		context.getWorldFactory().createPlayer(new Vector2f(7.5f, 2));

		Random random = new Random();
		for (int i = 0; i < 100; i++)
		{
			Enemy enemy = context.getWorldFactory().createEnemy(randomPosition(random));
			enemy.setVelocity(new Vector2f(0f, -(random.nextFloat() * 1.3f + .1f)));
		}
	}

	private Vector2f randomPosition(Random random)
	{
		return new Vector2f(random.nextFloat() * 13, 10);
	}

	private void processInput()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN)
				{
					getPlayer().moveDown();
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					getPlayer().moveUp();
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT)
				{
					getPlayer().moveRight();
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT)
				{
					getPlayer().moveLeft();
				}
			} else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN || Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					getPlayer().stopMoveVertical();
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT || Keyboard.getEventKey() == Keyboard.KEY_LEFT)
				{
					getPlayer().stopMoveHorizontal();
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
		while (lastFrameTime - lastWorldTime >= context.getWorldTimestep())
		{
			long now = lastWorldTime + (long) (context.getWorldTimestep() * 1000);
			context.setNowInMilliseconds(now);

			context.setMouseAim(getMouseAim());

			getWorldEntities().beforeWorldStep();

			context.getWorld().step(context.getWorldTimestep(), 8, 3);

			getWorldEntities().afterWorldStep();

			lastWorldTime = now;
		}

		removeDeadEntities();
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
		return context.getVectorConverter().convertToPixel(getPlayer().getCenterPosition());
	}

	private void renderObjects()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		getWorldEntities().render();

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
		GL11.glTranslatef(0, context.getDisplayHeight(), 0);
		GL11.glScalef(1, -1, 1);

		context.getTextFont().drawString(0, 0, Long.toString(lastFPS) + " fps", Color.white);

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
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	private int setFrameTime()
	{
		long time = getTime();
		int delta = (int) (time - lastFrameTime);
		lastFrameTime = time;

		return delta;
	}

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
