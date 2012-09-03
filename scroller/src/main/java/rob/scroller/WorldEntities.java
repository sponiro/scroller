package rob.scroller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import rob.scroller.entity.Bullet;
import rob.scroller.entity.Enemy;
import rob.scroller.entity.Entity;
import rob.scroller.entity.Player;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * All entities of the game world are assembled here.
 * 
 */
public class WorldEntities implements ISimulationAction
{
	private final ScrollerGameContext context;

	private Dungeon dungeon;

	private Player player;
	private List<Bullet> bullets;
	private List<Enemy> enemies;

	@Inject
	public WorldEntities(ScrollerGameContext context)
	{
		this.context = context;

		this.enemies = new ArrayList<Enemy>();
		this.bullets = new LinkedList<Bullet>();
	}

	public void render()
	{
		Vector2f origin = getRenderOrigin();

		dungeon.render(origin);

		for (Entity entity : getEntities())
		{
			entity.render(origin);
		}

		player.render(origin);
	}

	public Iterable<Entity> getEntities()
	{
		return Iterables.concat(bullets, enemies);
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public List<Bullet> getBullets()
	{
		return bullets;
	}

	public List<Enemy> getEnemies()
	{
		return enemies;
	}

	public Dungeon getDungeon()
	{
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon)
	{
		this.dungeon = dungeon;
	}

	public Vector2f getRenderOrigin()
	{
		Vector2f origin = Vector2f.sub(getPlayer().getCenterPosition(), context.getScreenCenter(), null);
		origin.setX(MathUtils.clamp(origin.getX(), 0, dungeon.getMaximumX()));
		origin.setY(MathUtils.clamp(origin.getY(), 0, dungeon.getMaximumY()));
		return origin;
	}

	public void addEnemy(Enemy enemy)
	{
		enemies.add(enemy);
	}

	public void addBullet(Bullet bullet)
	{
		bullets.add(bullet);
	}

	@Override
	public void simulateStep()
	{
		player.simulateStep();

		for (Enemy enemy : enemies)
		{
			enemy.simulateStep();
		}
	}
}
