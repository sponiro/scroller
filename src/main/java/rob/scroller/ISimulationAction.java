package rob.scroller;

public interface ISimulationAction
{
	void beforeWorldStep(ScrollerGameContext context);

	void afterWorldStep();
}
