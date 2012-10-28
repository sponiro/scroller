package rob.scroller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		dungeon.render(context.getRenderer());

		for (Entity entity : getEntities())
		{
			entity.render(context.getRenderer());
		}

		player.render(context.getRenderer());
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

	public void addEnemy(Enemy enemy)
	{
		enemies.add(enemy);
	}

	public void addBullet(Bullet bullet)
	{
		bullets.add(bullet);
	}

	@Override
	public void afterWorldStep()
	{
		player.afterWorldStep();

		for (Entity entity : getEntities())
		{
			entity.afterWorldStep();
		}
	}

	@Override
	public void beforeWorldStep()
	{
		player.beforeWorldStep();

		for (Entity entity : getEntities())
		{
			entity.beforeWorldStep();
		}
	}
}
