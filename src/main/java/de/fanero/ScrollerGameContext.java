package de.fanero;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import de.fanero.map.GameMap;
import de.fanero.map.IGameMapBuilder;
import de.fanero.stream.MapArchive;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.IOException;

public class ScrollerGameContext {

    private int displayWidth;
    private int displayHeight;
    private float tileSize;

    private Vector2f mouseAim;

    private UnicodeFont textFont;

    private GameMap gameMap;

    /**
     * Injections
     */
    private IGameMapBuilder gameMapBuilder;

    @Inject
    public ScrollerGameContext(Config config) {

        setDisplayWidth(config.getInt("scroller.width"));
        setDisplayHeight(config.getInt("scroller.height"));
        setTileSize(config.getInt("scroller.tile-size"));
    }

    @SuppressWarnings("unchecked")
    public void loadAndInit(MapArchive mapArchive) throws IOException, SlickException {

        gameMap = gameMapBuilder.load(mapArchive);

        textFont = new UnicodeFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFont.addAsciiGlyphs();
        textFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        textFont.loadGlyphs();
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public float getTileSize() {
        return tileSize;
    }

    public void setTileSize(float tileSize) {
        this.tileSize = tileSize;
    }

    public UnicodeFont getTextFont() {
        return textFont;
    }

    /**
     * Screen coordinates for the optimal player position
     *
     * @return
     */
    public Vector2f getScreenCenter() {
        Vector2f center = new Vector2f();
        center.setX(getDisplayWidth() / 2);
        center.setY(getDisplayHeight() / 2);
        return center;
    }

    public void setMouseAim(Vector2f mouseAim) {
        this.mouseAim = mouseAim;
    }

    public Vector2f getMouseAim() {
        return mouseAim;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    @Inject
    public void setGameMapBuilder(IGameMapBuilder gameMapBuilder) {
        this.gameMapBuilder = gameMapBuilder;
    }

}
