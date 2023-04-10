import java.awt.Color;

class BrushStroke {
	/*
	 * (x1, y1): initial point (x2, y2): end point
	 */
	final int x1;
	final int y1;
	final int x2;
	final int y2;
	final Color color;

	protected BrushStroke(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
}