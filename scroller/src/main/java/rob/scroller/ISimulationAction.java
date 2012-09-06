package rob.scroller;

public interface ISimulationAction
{
	void beforeWorldStep();

	public abstract void afterWorldStep();
}
