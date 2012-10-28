package rob.scroller.map;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CharacterPrototypeTest
{
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void bulletsNotNull() throws Exception
	{
		CharacterPrototype cp = new CharacterPrototype();

		assertNotNull(cp.getBulletPrototypes());
	}
}
