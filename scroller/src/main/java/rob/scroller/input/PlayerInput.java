package rob.scroller.input;

public class PlayerInput
{
	private ExclusiveKeys upDown;
	private ExclusiveKeys leftRight;

	public PlayerInput()
	{
		upDown = new ExclusiveKeys();
		leftRight = new ExclusiveKeys();
	}

	public void pressUp()
	{
		upDown.pressA();
	}

	public void pressDown()
	{
		upDown.pressB();
	}

	public void releaseUp()
	{
		upDown.releaseA();
	}

	public void releaseDown()
	{
		upDown.releaseB();
	}

	public VerticalDirection getVerticalDirection()
	{

		switch (upDown.getKeyState())
		{
		case KEY_A:
			return VerticalDirection.UP;
		case KEY_B:
			return VerticalDirection.DOWN;
		case NONE:
			return VerticalDirection.NONE;
		default:
			return VerticalDirection.NONE;
		}
	}

	public void pressLeft()
	{
		leftRight.pressA();
	}

	public void pressRight()
	{
		leftRight.pressB();
	}

	public void releaseLeft()
	{
		leftRight.releaseA();
	}

	public void releaseRight()
	{
		leftRight.releaseB();
	}

	public HorizontalDirection getHorizontalDirection()
	{
		switch (leftRight.getKeyState())
		{
		case KEY_A:
			return HorizontalDirection.LEFT;
		case KEY_B:
			return HorizontalDirection.RIGHT;
		case NONE:
			return HorizontalDirection.NONE;
		default:
			return HorizontalDirection.NONE;
		}
	}
}
