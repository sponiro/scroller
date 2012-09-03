package rob.scroller;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Dungeon
{
	private static final int TILE_SIZE = 64;

	private final ScrollerGameContext context;

	private int rows;
	private int columns;

	/* list of rows containing floor tiles */
	private List<List<Floor>> floorTiles;

	public Dungeon(ScrollerGameContext context, int columns, int rows)
	{
		this.context = context;
		this.columns = columns;
		this.rows = rows;

		this.floorTiles = new ArrayList<List<Floor>>(rows);
		for (int r = 0; r < rows; r++)
		{
			ArrayList<Floor> row = new ArrayList<Floor>(columns);

			for (int c = 0; c < columns; c++)
			{
				Floor floor = new Floor();
				floor.setTexture(context.getFloorTexture());
				row.add(floor);
			}

			floorTiles.add(row);
		}
	}

	public int getColumns()
	{
		return columns;
	}

	public int getRows()
	{
		return rows;
	}

	public void render(Vector2f origin)
	{
		origin.setX(MathUtils.clamp(origin.getX(), 0, getMaximumX()));
		origin.setY(MathUtils.clamp(origin.getY(), 0, getMaximumX()));

		int maxCol = mapCoordToTile(getMaximumDisplayX(origin));
		for (int col = mapCoordToTile((int) origin.getX()); col <= maxCol; col++)
		{
			int xCoord = mapTileToCoord(col);

			int maxRow = mapCoordToTile(getMaximumDisplayY(origin));
			for (int row = mapCoordToTile((int) origin.getY()); row <= maxRow; row++)
			{
				int yCoord = mapTileToCoord(row);

				Floor tile = getTile(col, row);

				context.getRenderer().blit(xCoord - (int) origin.getX(), yCoord
						- (int) origin.getY(), tile.getTexture());
			}
		}
	}

	private int getMaximumDisplayY(Vector2f origin)
	{
		return (int) origin.getY() + getDisplayHeight() - 1;
	}

	private int getMaximumDisplayX(Vector2f origin)
	{
		return (int) origin.getX() + getDisplayWidth() - 1;
	}

	private int getDisplayHeight()
	{
		return context.getDisplayHeight();
	}

	private int getDisplayWidth()
	{
		return context.getDisplayWidth();
	}

	public int getMaximumX()
	{
		return Math.max(0, getWidth() - 1);
	}

	public int getMaximumY()
	{
		return Math.max(0, getHeight() - 1);
	}

	private int getWidth()
	{
		if (floorTiles.size() > 0)
		{
			return floorTiles.get(0).size() * TILE_SIZE;
		} else
		{
			return 0;
		}
	}

	private int getHeight()
	{
		if (floorTiles.size() > 0)
		{
			return floorTiles.size() * TILE_SIZE;
		} else
		{
			return 0;
		}
	}

	public Floor getTile(int col, int row)
	{
		if (row < 0 || row >= floorTiles.size())
		{
			return getDefaultFloor();
		}

		List<Floor> rowList = floorTiles.get(row);

		if (col < 0 || col >= rowList.size())
		{
			return getDefaultFloor();
		}

		return rowList.get(col);
	}

	private int mapCoordToTile(int x)
	{
		return x / TILE_SIZE;
	}

	private int mapTileToCoord(int tileCoord)
	{
		return tileCoord * TILE_SIZE;
	}

	public Floor getDefaultFloor()
	{
		Floor floor = new Floor();
		floor.setTexture(context.getEmptyTexture());

		return floor;
	}
}
