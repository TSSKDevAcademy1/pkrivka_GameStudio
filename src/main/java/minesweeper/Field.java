package minesweeper;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
		// System.out.println(this.toString());
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			
			if (((Clue) tile).getValue() == 0) {
				openAdjacentTiles(row, column);
			}
			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile tile = getTile(row, column);
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		// nahodne vygeneruje miny
		Random ran = new Random();
		for (int i = 0; i < mineCount; i++) {
			int mine_row = ran.nextInt(rowCount);
			int mine_col = ran.nextInt(columnCount);
			if (tiles[mine_row][mine_col] == null) {
				tiles[mine_row][mine_col] = new Mine();
			} else {
				i--;
			}
		}
		// ak je dana pozicia prazdna, vygeneruje znacku
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				if (tiles[row][col] == null) {
					tiles[row][col] = new Clue(countAdjacentMines(row, col));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		return ((rowCount * columnCount) - getNumberOf(Tile.State.OPEN)) == getMineCount();
	}

	private int getNumberOf(Tile.State state) {
		int pocet = 0;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				if (tiles[row][col].getState() == state) {
					pocet++;
				}
			}
		}
		return pocet;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < getRowCount()) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < getRowCount()) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						openTile(actRow, actColumn);
					}
				}
			}
		}

	}
	
	public int getRemainingMineCount(){
		int number;
		number=mineCount-getNumberOf(Tile.State.MARKED);
		return number;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	@Override
	// vypise znak na danu poziciu
	public String toString() {
		String str = "";
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				str += (tiles[row][col].toString());
			}
			str += "\n";
		}
		return str;
	}

}
