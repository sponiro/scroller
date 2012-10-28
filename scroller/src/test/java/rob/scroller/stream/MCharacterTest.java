package rob.scroller.stream;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MCharacterTest
{
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void bulletsNotNull()
	{
		MCharacter c = new MCharacter();
		
		assertNotNull(c.getBullets());
	}

	@Test
	public void setBulletsDoesNotAllowNull() throws Exception
	{
		MCharacter c = new MCharacter();
		
		thrown.expect(NullPointerException.class);
		c.setBullets(null);
	}	
}
