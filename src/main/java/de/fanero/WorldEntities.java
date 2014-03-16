package de.fanero;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import de.fanero.entity.Bullet;
import de.fanero.entity.Enemy;
import de.fanero.entity.Entity;
import de.fanero.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * All entities of the game world are assembled here.
 */
public class WorldEntities implements ISimulationAction {

    private final IRenderer renderer;

    private Dungeon dungeon;

    private Player player;
    private List<Bullet> bullets;
    private List<Enemy> enemies;

    @Inject
    public WorldEntities(IRenderer renderer) {
        this.renderer = renderer;

        this.enemies = new ArrayList<Enemy>();
        this.bullets = new LinkedList<Bullet>();
    }

    public void render() {

        dungeon.render(renderer);

        for (Entity entity : getEntities()) {
            entity.render(renderer);
        }

        player.render(renderer);
    }

    public Iterable<Entity> getEntities() {
        return Iterables.concat(bullets, enemies);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (this.player != null) {
            this.player.destroy();
        }

        this.player = player;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    @Override
    public void beforeWorldStep() {
        player.beforeWorldStep();

        for (Entity entity : getEntities()) {
            entity.beforeWorldStep();
        }
    }

    @Override
    public void afterWorldStep() {
        player.afterWorldStep();

        for (Entity entity : getEntities()) {
            entity.afterWorldStep();
        }
    }
}
