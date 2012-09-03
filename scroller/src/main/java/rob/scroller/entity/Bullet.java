package rob.scroller.entity;

import rob.scroller.ScrollerGameContext;

public class Bullet extends Entity
{
	private int damage;

	public Bullet(ScrollerGameContext context)
	{
		super(context);

		this.damage = 1;
	}

	public int getDamage()
	{
		return damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}
}
