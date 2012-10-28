package rob.scroller.map;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import rob.scroller.stream.MBullet;
import rob.scroller.stream.MEnemy;
import rob.scroller.stream.MEntity;
import rob.scroller.stream.MMap;
import rob.scroller.stream.MPlayer;
import rob.scroller.stream.MapArchive;
import rob.scroller.stream.MapException;

public class GameMapBuilder
{
	private Map<String, Texture> textures;
	private GameMap gm;

	public GameMap load(MapArchive mapArchive)
	{
		try
		{
			textures = new HashMap<String, Texture>();
			gm = new GameMap();

			return loadInternal(mapArchive);
		} catch (IOException e)
		{
			throw new GameMapException();
		}
	}

	private GameMap loadInternal(MapArchive mapArchive) throws IOException
	{
		MMap mMap = mapArchive.getGameMap();

		loadTextures(mMap);

		for (MPlayer player : mMap.getPlayers())
		{
			addPlayer(player);
		}

		for (MEnemy enemy : mMap.getEnemies())
		{
			addEnemy(enemy);
		}

		for (MBullet bullet : mMap.getBullets())
		{
			addBullet(bullet);
		}

		for (MEntity entity : mMap.getGeneralEntities())
		{
			addGeneralEntity(entity);
		}

		for (MPlayer player : mMap.getPlayers())
		{
			PlayerPrototype playerPrototype = gm.getPlayerPrototype(player.getName());

			for (String bulletName : player.getBullets())
			{
				if (gm.getBulletPrototype(bulletName) == null)
				{
					throw new MapException(MessageFormat.format("Missing bullet \"{0}\"", bulletName));
				}

				playerPrototype.addBulletPrototype(gm.getBulletPrototype(bulletName));
			}
		}

		for (MEnemy enemy : mMap.getEnemies())
		{
			EnemyPrototype enemyPrototype = gm.getEnemyPrototype(enemy.getName());

			for (String bulletName : enemy.getBullets())
			{
				if (gm.getBulletPrototype(bulletName) == null)
				{
					throw new MapException(MessageFormat.format("Missing bullet \"{0}\"", bulletName));
				}

				enemyPrototype.addBulletPrototype(gm.getBulletPrototype(bulletName));
			}
		}

		return gm;
	}

	private void loadTextures(MMap mMap) throws IOException
	{
		for (MEntity entity : mMap.getAllEntities())
		{
			Texture texture = TextureLoader.getTexture("PNG", new ByteArrayInputStream(entity.getSpriteData()), true);
			textures.put(entity.getSprite(), texture);
		}
	}

	private void addPlayer(MPlayer player)
	{
		PlayerPrototype playerPrototype = new PlayerPrototype();
		setEntityValues(player, playerPrototype);

		gm.addPlayerPrototype(playerPrototype);
	}

	private void addEnemy(MEnemy enemy)
	{
		EnemyPrototype enemyPrototype = new EnemyPrototype();
		setEntityValues(enemy, enemyPrototype);

		gm.addEnemyPrototype(enemyPrototype);
	}

	private void addBullet(MBullet bullet)
	{
		BulletPrototype bulletPrototype = new BulletPrototype();
		setEntityValues(bullet, bulletPrototype);

		gm.addBulletPrototype(bulletPrototype);
	}

	private void addGeneralEntity(MEntity entity)
	{
		EntityPrototype entityPrototype = new EntityPrototype();
		setEntityValues(entity, entityPrototype);

		gm.addGeneralPrototype(entityPrototype);
	}

	private void setEntityValues(MEntity entity, EntityPrototype entityPrototype)
	{
		entityPrototype.setName(entity.getName());
		entityPrototype.setTexture(textures.get(entity.getSprite()));
	}
}
