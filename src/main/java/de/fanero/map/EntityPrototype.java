package de.fanero.map;

import com.google.common.base.Preconditions;
import org.newdawn.slick.opengl.Texture;

public class EntityPrototype {
    private String name;
    private Texture texture;

    public EntityPrototype() {
        name = "";
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name);

        this.name = name;
    }
}
