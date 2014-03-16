package de.fanero.map;

import de.fanero.stream.MapArchive;

public interface IGameMapBuilder {

    GameMap load(MapArchive mapArchive);
}
