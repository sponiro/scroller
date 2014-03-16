package de.fanero;

import com.google.inject.Inject;
import de.fanero.stream.IMapLoader;
import de.fanero.stream.MapArchive;

public class GameLoader implements IGameLoader {

    private IMapLoader mapLoader;
    private IScrollerGame scrollerGame;

    @Override
    public void load(String zipFilename) {

        MapArchive mapArchive = mapLoader.loadMapArchive(zipFilename);
        scrollerGame.start(mapArchive);
    }

    @Inject
    public void setScrollerGame(IScrollerGame scrollerGame) {
        this.scrollerGame = scrollerGame;
    }

    @Inject
    public void setMapLoader(IMapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }
}
