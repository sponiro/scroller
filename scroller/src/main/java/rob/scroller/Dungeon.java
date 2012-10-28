package rob.scroller;

import java.util.ArrayList;
import java.util.List;

import rob.scroller.map.EntityPrototype;

public class Dungeon
{
	private static final float SPEED = 1.5f;

	private final ScrollerGameContext context;

	private int rows;
	private int columns;

	private List<List<Floor>> floorTiles;

	private int totalRows;
	private long start;

	/**
	 * @param context
	 * @param columns
	 * @param rows
	 */
	public Dungeon(ScrollerGameContext context, int columns, int rows)
	{
		this.context = context;
		this.columns = columns;
		this.rows = rows;

		this.floorTiles = new ArrayList<List<Floor>>(rows);
		
		totalRows = 2 * rows;
		
		for (int r = 0; r < totalRows; r++)
		{
			ArrayList<Floor> row = new ArrayList<Floor>(columns);

			for (int c = 0; c < columns; c++)
			{
				Floor floor = new Floor();
				EntityPrototype generalPrototype = context.getGameMap().getGeneralPrototype("floor");
				floor.setTexture(generalPrototype.getTexture());

				row.add(floor);
			}

			floorTiles.add(row);
		}
	}

	public void resetToStart()
	{
		start = context.getNowInMilliseconds();
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
		for (int col = 0; col < columns; col++)
		{
			for (int rowOffset = 0; rowOffset <= rows; rowOffset++)
			{
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
	private float rowToCoord(int row)
	{
		return row - getCurrentOffset();
	}

	private int getCurrentRow(int rowOffset)
	{
		return (int) getCurrentOffset() + rowOffset;
	}

	private float getCurrentOffset()
	{
		return SPEED * getTimeDelta() / 1000;
	}

	private long getTimeDelta()
	{
		return context.getNowInMilliseconds() - start;
	}

	public Floor getTile(int col, int row)
	{
		row = row % totalRows;
		
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
//		floor.setTexture(context.getEmptyTexture());

		return floor;
	}
}
