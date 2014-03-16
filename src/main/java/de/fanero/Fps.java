package de.fanero;

import org.lwjgl.Sys;

public class Fps {

    /**
     * current frames per second
     */
    private int fpsCounter;

    /**
     * last fps time measure
     */
    private long lastFpsTime;

    /**
     * last fps measure
     */
    private float fps;

    public Fps(long lastFpsTime) {
        this.lastFpsTime = lastFpsTime;
        this.fpsCounter = 0;
        this.fps = 0;
    }

    public void incFpsCounter() {

        long time = getTime();
        long diff = time - lastFpsTime;

        if (diff > 1000) {
            fps = (float) fpsCounter / diff * 1000;
            fpsCounter = 0;
            lastFpsTime = time;
        }

        fpsCounter++;
    }

    public float getFps() {
        return fps;
    }

    /**
     * Get the time in milliseconds
     *
     * @return The system time in milliseconds
     */
    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}
