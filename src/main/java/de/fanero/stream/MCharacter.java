package de.fanero.stream;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class MCharacter extends MEntity {
    private List<String> bullets;

    public MCharacter() {
        bullets = new ArrayList<String>();
    }

    public List<String> getBullets() {
        return bullets;
    }

    public void setBullets(List<String> bullets) {
        Preconditions.checkNotNull(bullets);

        this.bullets = bullets;
    }
}
