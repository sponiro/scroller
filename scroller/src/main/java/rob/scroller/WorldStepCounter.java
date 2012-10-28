package rob.scroller;

public class WorldStepCounter
{
	private long worldTime;
	private long currentTime;
	private long worldStep;

	public WorldStepCounter(float worldStep)
	{
		this.worldStep = (long) (worldStep * 1000);

		currentTime = 0;
		worldTime = 0;
	}

	public void reset(long time)
	{
		currentTime = time;
		worldTime = time;
	}

	public long getWorldTime()
	{
		return worldTime;
	}

	public long getCurrentTime()
	{
		return currentTime;
	}

	public void updateCurrentTime(long currentTime)
	{
		this.currentTime = currentTime;
	}

	public boolean needsStep()
	{
		return currentTime - worldTime >= worldStep;
	}

	public void step()
	{
		worldTime += worldStep;
	}
}
