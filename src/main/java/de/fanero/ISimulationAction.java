package de.fanero;

public interface ISimulationAction {
    void beforeWorldStep(ScrollerGameContext context);

    void afterWorldStep();
}
