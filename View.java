import java.awt.image.BufferedImage;

public class View {
	protected BufferedImage image;
	protected int x, y;
	
	public View(BufferedImage image, int x, int y) {
		this.image = image;
		setCorner(x, y);
	}
	
//	moving the view to the specified position
	protected void setCorner(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
//	X coordinate of the upper left corner of the image
	protected int getCornerX() {
		return x;
	}
	
// Y coordinate of the upper left corner of the image
	protected int getCornerY() {
		return y;
	}
	
//	Checking if the coordinate is on the left corner of the image
	protected boolean isAtLeftEdge() {
		return getCornerX() == 0;
	}
	
//	Checking if the coordinate is on the top corner of the image
	protected boolean isAtTopEdge() {
		return getCornerY() == 0;
	}
	
//	Checking the bounds and taking the color value from the pixel if it is within bounds.
	protected int[] getSample (int x, int y) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] output = new int[3];
		
		for(int i = 0; i < width ; i++) {
			for(int j = 0; j < height; j++) {
				int r,g,b;
				int rgb = image.getRGB(i, j);
				
				r = getRed(rgb);
				g = getGreen(rgb);
				b = getBlue(rgb);
				
				output [1] = r;
				output [2] = g;
				output [3] = b;
			}
		}
		return output;
	}
	
	private int clip(int v) {
		v = v > 255 ? 255 : v;
		v = v < 0 ? 0 : v;
		return v;
	}

	protected int getRed(int pixel) {
		return (pixel >>> 16) & 0xFF;
	}

	protected int getGreen(int pixel) {
		return (pixel >>> 8) & 0xFF;
	}

	protected int getBlue(int pixel) {
		return pixel & 0xFF;
	}
}
