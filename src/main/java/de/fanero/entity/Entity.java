package de.fanero.entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import de.fanero.IRenderer;
import de.fanero.ISimulationAction;
import de.fanero.ScrollerGameContext;

/**
 * Graphical entity with a position, velocity and texture
 */
public abstract class Entity implements ISimulationAction {
    private Texture texture;
    private float width;
    private float height;

    private boolean markedForRemoval;
    private Body body;

    public Entity() {
        this.markedForRemoval = false;
        this.width = 1;
        this.height = 1;
    }

    public abstract Body createBody(World world, Vector2f position);

    public Vector2f getVelocity() {
        return new Vector2f(body.getLinearVelocity().x, body.getLinearVelocity().y);
    }

    public void setVelocity(Vector2f velocity) {
        body.setLinearVelocity(new Vec2(velocity.x, velocity.y));
    }

    public Vector2f getPosition() {
        Vector2f position = new Vector2f();
        position.x = getCenterPosition().x - width / 2;
        position.y = getCenterPosition().y - height / 2;
        return position;
    }

    public Vector2f getCenterPosition() {
        return new Vector2f(body.getPosition().x, body.getPosition().y);
    }

    public void render(IRenderer renderer) {
        Vector2f p = getPosition();

        renderer.blitClamped(p.x, p.y, width, height, getTexture());
    }

    public void isHitBy(Entity entity) {

    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void markForRemoval() {
        this.markedForRemoval = true;
    }

    public void destroy() {
        body.getWorld().destroyBody(body);
    }

    @Override
    public void beforeWorldStep(ScrollerGameContext context) {

    }

    @Override
    public void afterWorldStep() {

    }

    public Body getBody() {
        return body;
    }

    protected void setBody(Body body) {
        this.body = body;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
