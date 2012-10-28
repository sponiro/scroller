package rob.scroller.map;

import java.util.ArrayList;
import java.util.List;

public class CharacterPrototype extends EntityPrototype
{
	private List<BulletPrototype> bulletPrototypes;

	public CharacterPrototype()
	{
		bulletPrototypes = new ArrayList<BulletPrototype>();
	}

	public List<BulletPrototype> getBulletPrototypes()
	{
		return bulletPrototypes;
	}

	public void addBulletPrototype(BulletPrototype bulletPrototype)
	{
		bulletPrototypes.add(bulletPrototype);
	}
}