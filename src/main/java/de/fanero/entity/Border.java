package de.fanero.entity;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.vector.Vector2f;


public class Border extends Entity {
    @Override
    public Body createBody(World world, Vector2f position) {
        return null;
    }
}
