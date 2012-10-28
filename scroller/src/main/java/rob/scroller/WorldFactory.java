package rob.scroller;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.entity.Border;
import rob.scroller.entity.Bullet;
import rob.scroller.entity.Enemy;
import rob.scroller.entity.EnemyBullet;
import rob.scroller.entity.Player;
import rob.scroller.map.BulletPrototype;
import rob.scroller.map.EnemyPrototype;
import rob.scroller.map.PlayerPrototype;

import com.google.inject.Inject;

public class WorldFactory
{
	private final ScrollerGameContext context;

	@Inject
	public WorldFactory(ScrollerGameContext context)
	{
		this.context = context;
	}

	public Player createPlayer(Vector2f position, PlayerPrototype prototype)
	{
		Player player = new Player(context, position);
		player.setTexture(prototype.getTexture());
		player.setBulletPrototype(prototype.getBulletPrototypes().get(0));

		context.getWorldEntities().setPlayer(player);

		return player;
	}

	public Enemy createEnemy(Vector2f position, EnemyPrototype prototype)
	{
		Enemy enemy = new Enemy(context, position);
		enemy.setTexture(prototype.getTexture());
		enemy.setBulletPrototype(prototype.getBulletPrototypes().get(0));
		enemy.setWidth(1);
		enemy.setHeight(1);

		context.getWorldEntities().addEnemy(enemy);

		return enemy;
	}

	public Bullet createBullet(Vector2f position, BulletPrototype prototype)
	{
		Bullet bullet = new Bullet(context, position);
		bullet.setTexture(prototype.getTexture());
		bullet.setWidth(.25f);
		bullet.setHeight(.25f);

		context.getWorldEntities().addBullet(bullet);

		return bullet;
	}

	public Bullet createEnemyBullet(Vector2f position, BulletPrototype prototype)
	{
		EnemyBullet bullet = new EnemyBullet(context, position);
		bullet.setTexture(prototype.getTexture());
		bullet.setWidth(.25f);
		bullet.setHeight(.25f);

		context.getWorldEntities().addBullet(bullet);

		return bullet;
	}

	public Dungeon createDungeon(int columns, int rows)
	{
		Dungeon dungeon = new Dungeon(context, columns, rows);

		createDungeonBorder(dungeon);

		context.getWorldEntities().setDungeon(dungeon);

		return dungeon;
	}

	private void createDungeonBorder(float width, float height, float distance, float borderWidth)
	{
		Vec2 v1 = new Vec2();
		Vec2 v2 = new Vec2(width, height);

		float halfBorderWidth = borderWidth / 2;
		float halfheight = Math.abs(v1.y - v2.y) / 2 + distance + borderWidth;
		float halfwidth = Math.abs(v1.x - v2.x) / 2 + distance + borderWidth;

		Vec2 vRight = new Vec2();
		vRight.x = Math.max(v1.x, v2.x) + halfBorderWidth + distance;
		vRight.y = (v1.y + v2.y) / 2;

		Vec2 vLeft = new Vec2();
		vLeft.x = Math.min(v1.x, v2.x) - halfBorderWidth - distance;
		vLeft.y = vRight.y;

		Vec2 vTop = new Vec2();
		vTop.x = (v1.x + v2.x) / 2;
		vTop.y = Math.max(v1.y, v2.y) + halfBorderWidth + distance * 4;

		Vec2 vBottom = new Vec2();
		vBottom.x = vTop.x;
		vBottom.y = Math.min(v1.y, v2.y) - halfBorderWidth - distance;

		createBorder(vRight, halfBorderWidth, halfheight);
		createBorder(vLeft, halfBorderWidth, halfheight);
		createBorder(vTop, halfwidth, halfBorderWidth);
		createBorder(vBottom, halfwidth, halfBorderWidth);
	}

	private void createDungeonBorder(Dungeon dungeon)
	{
		createDungeonBorder(dungeon.getColumns(), dungeon.getRows(), 1, 1);
	}

	private Body createBorder(Vec2 position, float halfwidth, float halfheight)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(halfwidth, halfheight);

		Body body = context.getWorld().createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.filter.categoryBits = 8;
		fixtureDef.filter.maskBits = 0xffff;

		body.createFixture(fixtureDef);
		body.setUserData(new Border(context, new Vector2f()));

		return body;
	}
}
