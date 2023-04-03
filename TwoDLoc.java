
public class TwoDLoc {
	protected int row, col;
	
	public TwoDLoc(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	protected void set(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	protected int getRow() {
		return row;
	}
	
	protected int getCol() {
		return col;
	}
	
	protected int getX() {
		return row;
	}
	
	protected int getY() {
		return col;
	}
}
