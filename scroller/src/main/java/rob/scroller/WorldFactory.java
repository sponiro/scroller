package rob.scroller;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;

import rob.scroller.entity.Bullet;
import rob.scroller.entity.Enemy;
import rob.scroller.entity.Player;
import rob.scroller.entity.SmallEnemy;

import com.google.inject.Inject;

public class WorldFactory
{
	private final ScrollerGameContext context;

	@Inject
	public WorldFactory(ScrollerGameContext context)
	{
		this.context = context;
	}

	public Player createPlayer(Vector2f position)
	{
		Player player = new Player(context, position);
		player.setTexture(context.getPlayerTexture());

		context.getWorldEntities().setPlayer(player);

		return player;
	}

	public Enemy createEnemy(Vector2f position)
	{
		Enemy enemy = new Enemy(context, position);
		enemy.setTexture(context.getEnemyTexture());
		enemy.setWidth(1);
		enemy.setHeight(1);

		context.getWorldEntities().addEnemy(enemy);

		return enemy;
	}

	public Enemy createSmallEnemy(Vector2f position)
	{
		Enemy enemy = new SmallEnemy(context, position);
		enemy.setTexture(context.getEnemySimpleTexture());
		enemy.setWidth(1);
		enemy.setHeight(1);

		context.getWorldEntities().addEnemy(enemy);

		return enemy;
	}

	public Bullet createBullet(Vector2f position)
	{
		Bullet bullet = new Bullet(context, position);
		bullet.setTexture(context.getBulletTexture());
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

	private void createDungeonBorder(Dungeon dungeon)
	{
		Vec2 v1 = new Vec2();
		Vec2 v2 = new Vec2(dungeon.getColumns(), dungeon.getRows());

		Vec2 vLeft = new Vec2();
		Vec2 vRight = new Vec2();
		vRight.x = Math.max(v1.x, v2.x) + .01f;
		vLeft.x = Math.min(v1.x, v2.x) - .01f;
		vRight.y = (v1.y + v2.y) / 2;
		vLeft.y = vRight.y;

		Vec2 vTop = new Vec2();
		Vec2 vBottom = new Vec2();
		vTop.x = (v1.x + v2.x) / 2;
		vBottom.x = vTop.x;
		vTop.y = Math.max(v1.y, v2.y) + .01f;
		vBottom.y = Math.min(v1.y, v2.y) - 1.06f;

		float halfheight = Math.abs(v1.y - v2.y) / 2;
		float halfwidth = Math.abs(v1.x - v2.x) / 2;

		createBorder(vRight, .01f, halfheight);
		createBorder(vLeft, .01f, halfheight);
		createBorder(vTop, halfwidth, .01f);
		createBorder(vBottom, halfwidth, .01f);
//		Body bottomBodyBorder = createBorder(vBottom, halfwidth, .01f);
//		bottomBodyBorder.setUserData(new Border());
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

		return body;
	}
}
