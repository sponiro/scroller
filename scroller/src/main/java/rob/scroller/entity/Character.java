package rob.scroller.entity;

import rob.scroller.ScrollerGameContext;

public class Character extends Entity
{
	private int life;

	public Character(ScrollerGameContext context)
	{
		super(context);
		
		this.life = 2;
	}

	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}

	@Override
	public void isHitBy(Bullet bullet)
	{
		life = life - bullet.getDamage();
		
		bullet.markForRemoval();
		checkForDeath();
	}

	private void checkForDeath()
	{
		if (life <= 0) {
			markForRemoval();
		}
	}
}
