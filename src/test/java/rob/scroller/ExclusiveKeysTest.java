package rob.scroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import rob.scroller.input.ExclusiveKeys;
import rob.scroller.input.ExclusiveKeys.KEYSTATE;

public class ExclusiveKeysTest
{

	@Test
	public void keyANotPressed()
	{
		ExclusiveKeys input = new ExclusiveKeys();

		assertFalse(input.isAPressed());
		assertEquals(KEYSTATE.NONE, input.getKeyState());
	}

	@Test
	public void pressA()
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressA();

		assertTrue(input.isAPressed());
		assertEquals(KEYSTATE.KEY_A, input.getKeyState());
	}

	@Test
	public void keyBNotPressed() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		assertFalse(input.isBPressed());
		assertEquals(KEYSTATE.NONE, input.getKeyState());
	}

	@Test
	public void pressB() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressB();

		assertTrue(input.isBPressed());
		assertEquals(KEYSTATE.KEY_B, input.getKeyState());
	}

	@Test
	public void keyAThenKeyB() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressA();
		input.pressB();

		assertTrue(input.isBPressed());
		assertFalse(input.isAPressed());
		assertEquals(KEYSTATE.KEY_B, input.getKeyState());
	}

	@Test
	public void keyBThenKeyA() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressB();
		input.pressA();

		assertTrue(input.isAPressed());
		assertFalse(input.isBPressed());
		assertEquals(KEYSTATE.KEY_A, input.getKeyState());
	}

	@Test
	public void keyAKeyBReleaseB() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressA();
		input.pressB();
		input.releaseB();

		assertFalse(input.isBPressed());
		assertTrue(input.isAPressed());
		assertEquals(KEYSTATE.KEY_A, input.getKeyState());
	}

	@Test
	public void keyBkeyAReleaseA() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressB();
		input.pressA();
		input.releaseA();

		assertFalse(input.isAPressed());
		assertTrue(input.isBPressed());
		assertEquals(KEYSTATE.KEY_B, input.getKeyState());
	}

	@Test
	public void doublePressA() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressA();
		input.pressB();
		input.pressA();

		assertFalse(input.isAPressed());
		assertTrue(input.isBPressed());
		assertEquals(KEYSTATE.KEY_B, input.getKeyState());
	}

	@Test
	public void doublePressB() throws Exception
	{
		ExclusiveKeys input = new ExclusiveKeys();

		input.pressB();
		input.pressA();
		input.pressB();

		assertFalse(input.isBPressed());
		assertTrue(input.isAPressed());
		assertEquals(KEYSTATE.KEY_A, input.getKeyState());
	}
}
