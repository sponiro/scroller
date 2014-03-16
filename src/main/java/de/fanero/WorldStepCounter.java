package de.fanero;

import org.jbox2d.dynamics.World;

public class WorldStepCounter {

    private final World world;
    private long worldTime;
    private long currentTime;
    private long worldStep;

    public WorldStepCounter(float worldStep, World world) {
        this.worldStep = (long) (worldStep * 1000);
        this.world = world;

        currentTime = 0;
        worldTime = 0;
    }

    public void reset(long time) {
        currentTime = time;
        worldTime = time;
    }

    public long getWorldTime() {
        return worldTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void updateCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public boolean needsStep() {
        return currentTime - worldTime >= worldStep;
    }

    public void step() {

        worldTime += worldStep;
        world.step(worldStep / 1000f, 8, 3);
    }

    /**
     *
     * @return world step in milliseconds
     */
    public long getWorldStep() {
        return worldStep;
    }

    public World getWorld() {
        return world;
    }
}
