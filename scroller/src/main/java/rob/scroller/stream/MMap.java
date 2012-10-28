package rob.scroller.stream;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

public class MMap
{
	private String name;

	private List<MPlayer> players;
	private List<MEnemy> enemies;
	private List<MBullet> bullets;
	private List<MEntity> generalEntities;

	public MMap()
	{
		name = "";
		players = new ArrayList<MPlayer>();
		enemies = new ArrayList<MEnemy>();
		bullets = new ArrayList<MBullet>();
		generalEntities = new ArrayList<MEntity>();
	}

	public List<MBullet> getBullets()
	{
		return bullets;
	}

	public void setBullets(List<MBullet> bullets)
	{
		Preconditions.checkNotNull(bullets);

		this.bullets = bullets;
	}

	public List<MPlayer> getPlayers()
	{
		return players;
	}

	public void setPlayers(List<MPlayer> players)
	{
		Preconditions.checkNotNull(players);

		this.players = players;
	}

	public List<MEnemy> getEnemies()
	{
		return enemies;
	}

	public void setEnemies(List<MEnemy> enemies)
	{
		Preconditions.checkNotNull(enemies);

		this.enemies = enemies;
	}

	public List<MEntity> getGeneralEntities()
	{
		return generalEntities;
	}

	public void setGeneralEntities(List<MEntity> generalEntities)
	{
		Preconditions.checkNotNull(generalEntities);
		
		this.generalEntities = generalEntities;
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

	@JsonIgnore
	public Iterable<MEntity> getAllEntities()
	{
		return Iterables.concat(getPlayers(), getEnemies(), getBullets(), getGeneralEntities());
	}
}
