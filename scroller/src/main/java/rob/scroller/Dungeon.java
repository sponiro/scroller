package rob.scroller;

import java.util.ArrayList;
import java.util.List;

public class Dungeon
{
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

	public void render(IRenderer renderer)
	{
		for (int col = 0; col < columns; col++) {
			
			for (int row = 0; row < rows; row++)
			{
				Floor tile = getTile(col, row);
				
				renderer.blit(col, row, 1, 1, tile.getTexture());				
			}
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

	public Floor getDefaultFloor()
	{
		Floor floor = new Floor();
		floor.setTexture(context.getEmptyTexture());

		return floor;
	}
}
