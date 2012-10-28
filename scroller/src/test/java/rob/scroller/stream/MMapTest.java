package rob.scroller.stream;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MMapTest
{

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void bulletsNotNull()
	{
		MMap m = new MMap();

		assertNotNull(m.getBullets());
	}

	@Test
	public void setBulletsDoesNotAllowNull() throws Exception
	{
		MMap m = new MMap();

		thrown.expect(NullPointerException.class);
		m.setBullets(null);
	}

	@Test
	public void enemiesNotNull()
	{
		MMap m = new MMap();

		assertNotNull(m.getEnemies());
	}

	@Test
	public void setEnemiesDoesNotAllowNull() throws Exception
	{
		MMap m = new MMap();

		thrown.expect(NullPointerException.class);
		m.setEnemies(null);
	}

	@Test
	public void playersNotNull()
	{
		MMap m = new MMap();

		assertNotNull(m.getPlayers());
	}

	@Test
	public void setPlayersDoesNotAllowNull() throws Exception
	{
		MMap m = new MMap();

		thrown.expect(NullPointerException.class);
		m.setPlayers(null);
	}

	@Test
	public void generalEntitiesNotNull()
	{
		MMap m = new MMap();

		assertNotNull(m.getGeneralEntities());
	}

	@Test
	public void setGeneralEntitiesDoesNotAllowNull() throws Exception
	{
		MMap m = new MMap();

		thrown.expect(NullPointerException.class);
		m.setGeneralEntities(null);
	}

	@Test
	public void nameNotNull() throws Exception
	{
		MMap m = new MMap();

		assertNotNull(m.getName());
	}

	@Test
	public void setNamedoesNotAllowNull() throws Exception
	{
		MMap m = new MMap();

		thrown.expect(NullPointerException.class);
		m.setName(null);
	}

}
