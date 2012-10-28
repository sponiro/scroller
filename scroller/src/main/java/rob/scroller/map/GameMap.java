package rob.scroller.map;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rob.scroller.stream.MapException;

public class GameMap
{
	private Map<String, EnemyPrototype> enemyPrototypes;
	private Map<String, PlayerPrototype> playerPrototypes;
	private Map<String, BulletPrototype> bulletPrototypes;
	private Map<String, EntityPrototype> generalPrototypes;

	public GameMap()
	{
		enemyPrototypes = new HashMap<String, EnemyPrototype>();
		playerPrototypes = new HashMap<String, PlayerPrototype>();
		bulletPrototypes = new HashMap<String, BulletPrototype>();
		generalPrototypes = new HashMap<String, EntityPrototype>();
	}

	public PlayerPrototype getPlayerPrototype(String name)
	{
		return playerPrototypes.get(name);
	}

	public Map<String, PlayerPrototype> getPlayerPrototypes()
	{
		return Collections.unmodifiableMap(playerPrototypes);
	}

	public EnemyPrototype getEnemyPrototype(String name)
	{
		return enemyPrototypes.get(name);
	}

	public Map<String, EnemyPrototype> getEnemyPrototypes()
	{
		return Collections.unmodifiableMap(enemyPrototypes);
	}

	public BulletPrototype getBulletPrototype(String name)
	{
		return bulletPrototypes.get(name);
	}

	public Map<String, BulletPrototype> getBulletPrototypes()
	{
		return Collections.unmodifiableMap(bulletPrototypes);
	}

	public EntityPrototype getGeneralPrototype(String name)
	{
		return generalPrototypes.get(name);
	}

	public Map<String, EntityPrototype> getGeneralPrototypes()
	{
		return Collections.unmodifiableMap(generalPrototypes);
	}

	public void addPlayerPrototype(PlayerPrototype pp)
	{
		checkForDuplicate(playerPrototypes, pp);
		playerPrototypes.put(pp.getName(), pp);
	}

	public void addBulletPrototype(BulletPrototype bulletPrototype)
	{
		checkForDuplicate(bulletPrototypes, bulletPrototype);
		bulletPrototypes.put(bulletPrototype.getName(), bulletPrototype);
	}

	public void addEnemyPrototype(EnemyPrototype enemyPrototype)
	{
		checkForDuplicate(enemyPrototypes, enemyPrototype);
		enemyPrototypes.put(enemyPrototype.getName(), enemyPrototype);
	}

	public void addGeneralPrototype(EntityPrototype generalPrototype)
	{
		checkForDuplicate(generalPrototypes, generalPrototype);
		generalPrototypes.put(generalPrototype.getName(), generalPrototype);
	}

	private void checkForDuplicate(Map<String, ? extends EntityPrototype> prototypes, EntityPrototype entityPrototype)
	{
		if (prototypes.containsKey(entityPrototype.getName()))
		{
			throw new MapException(MessageFormat.format("Duplicate named entity {0}", entityPrototype.getName()));
		}
	}
}
