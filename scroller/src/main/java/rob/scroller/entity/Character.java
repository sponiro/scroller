package rob.scroller.entity;

public abstract class Character extends Entity
{
	private int life;

	public Character()
	{
		this.life = 2;
	}

	@Override
	public void isHitBy(Entity entity)
	{
		if (entity instanceof Bullet)
		{
			Bullet bullet = (Bullet) entity;

			life = life - bullet.getDamage();

			checkForDeath();
		}
	}

	private void checkForDeath()
	{
		if (life <= 0)
		{
			markForRemoval();
		}
	}

	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}
}
