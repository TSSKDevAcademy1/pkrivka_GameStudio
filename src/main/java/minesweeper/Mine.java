package minesweeper;

/**
 * Mine tile.
 */
public class Mine extends Tile {

	public String toString() {
		if (this.getState()==State.OPEN){
			return "* ";
		}
		else {
			return super.toString();
		}
	}

}
