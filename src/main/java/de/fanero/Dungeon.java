package de.fanero;

import com.google.inject.Inject;
import de.fanero.entity.Border;
import de.fanero.map.EntityPrototype;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    private static final float SPEED = 1.5f;

    /**
     * Injectons
     */
    private final ScrollerGameContext context;
    private WorldStepCounter worldStepCounter;

    private int rows;
    private int columns;

    private List<List<Floor>> floorTiles;

    private int totalRows;
    private long start;

    @Inject
    public Dungeon(ScrollerGameContext context) {
        this.context = context;
    }

    public void init(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        this.floorTiles = new ArrayList<List<Floor>>(rows);

        totalRows = 2 * rows;

        for (int r = 0; r < totalRows; r++) {
            ArrayList<Floor> row = new ArrayList<Floor>(columns);

            for (int c = 0; c < columns; c++) {
                Floor floor = new Floor();
                EntityPrototype generalPrototype = context.getGameMap().getGeneralPrototype("floor");
                floor.setTexture(generalPrototype.getTexture());

                row.add(floor);
            }

            floorTiles.add(row);
        }

        createDungeonBorder(this.columns, this.rows, 1, 1);
    }

    private void createDungeonBorder(float width, float height, float distance, float borderWidth) {

        Vec2 v1 = new Vec2();
        Vec2 v2 = new Vec2(width, height);

        float halfBorderWidth = borderWidth / 2;
        float halfheight = Math.abs(v1.y - v2.y) / 2 + distance + borderWidth;
        float halfwidth = Math.abs(v1.x - v2.x) / 2 + distance + borderWidth;

        Vec2 vRight = new Vec2();
        vRight.x = Math.max(v1.x, v2.x) + halfBorderWidth + distance;
        vRight.y = (v1.y + v2.y) / 2;

        Vec2 vLeft = new Vec2();
        vLeft.x = Math.min(v1.x, v2.x) - halfBorderWidth - distance;
        vLeft.y = vRight.y;

        Vec2 vTop = new Vec2();
        vTop.x = (v1.x + v2.x) / 2;
        vTop.y = Math.max(v1.y, v2.y) + halfBorderWidth + distance * 4;

        Vec2 vBottom = new Vec2();
        vBottom.x = vTop.x;
        vBottom.y = Math.min(v1.y, v2.y) - halfBorderWidth - distance;

        createBorder(vRight, halfBorderWidth, halfheight);
        createBorder(vLeft, halfBorderWidth, halfheight);
        createBorder(vTop, halfwidth, halfBorderWidth);
        createBorder(vBottom, halfwidth, halfBorderWidth);
    }

    private Body createBorder(Vec2 position, float halfwidth, float halfheight) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfwidth, halfheight);

        Body body = worldStepCounter.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = 8;
        fixtureDef.filter.maskBits = 0xffff;

        body.createFixture(fixtureDef);
        body.setUserData(new Border());

        return body;
    }

    public void resetToStart() {
        start = worldStepCounter.getWorldTime();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void render(IRenderer renderer) {
        for (int col = 0; col < columns; col++) {
            for (int rowOffset = 0; rowOffset <= rows; rowOffset++) {
                int currentRow = getCurrentRow(rowOffset);

                Floor floor = getTile(col, currentRow);

                renderer.blitRepeated(col, rowToCoord(currentRow), 1, 1, floor.getTexture());
            }
        }
    }

    /**
     * Calculates the screen coordinate to start painting from
     *
     * @param row
     * @return
     */
    private float rowToCoord(int row) {
        return row - getCurrentOffset();
    }

    private int getCurrentRow(int rowOffset) {
        return (int) getCurrentOffset() + rowOffset;
    }

    private float getCurrentOffset() {
        return SPEED * getTimeDelta() / 1000;
    }

    private long getTimeDelta() {
        return worldStepCounter.getWorldTime() - start;
    }

    public Floor getTile(int col, int row) {
        row = row % totalRows;

        if (row < 0 || row >= floorTiles.size()) {
            return getDefaultFloor();
        }

        List<Floor> rowList = floorTiles.get(row);

        if (col < 0 || col >= rowList.size()) {
            return getDefaultFloor();
        }

        return rowList.get(col);
    }

    public Floor getDefaultFloor() {
        Floor floor = new Floor();
        // floor.setTexture(context.getEmptyTexture());

        return floor;
    }

    @Inject
    public void setWorldStepCounter(WorldStepCounter worldStepCounter) {
        this.worldStepCounter = worldStepCounter;
    }
}
