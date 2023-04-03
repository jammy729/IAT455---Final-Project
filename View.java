import java.awt.Color;
import java.awt.image.BufferedImage;

public class View {
	protected BufferedImage image;
	protected int xOffset, yOffset;
	
	public View(BufferedImage image, int x, int y) {
		this.image = image;
		setCorner(x, y);
	}
	
//	moving the view to the specified position
	protected void setCorner(int x, int y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
//	X coordinate of the upper left corner of the image
	protected int getCornerX() {
		return xOffset;
	}
	
// Y coordinate of the upper left corner of the image
	protected int getCornerY() {
		return yOffset;
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
		int[] out = new int[3];
		int r,g,b;
		int rgb = image.getRGB(x, y);
		
		r = getRed(rgb);
		g = getGreen(rgb);
		b = getBlue(rgb);
		
		out[1] = r;
		out[2] = g;
		out[3] = b;
		
		
		return out;		
	}
	
	protected void putSample(int x, int y, int [] newvals) {
		int r, g, b;
		newvals = getSample(x, y);
		r = newvals[1];
		g = newvals[2];
		b = newvals[3];
		
		int rgb = new Color(r, g, b).getRGB();
		image.setRGB(x, y, rgb);
	}
	
	protected int imageX(int x) {
		return x + xOffset;
	}
	
	protected int imageY(int y) {
		return y + yOffset;
	}
	
	protected BufferedImage getImage() {
		return image;
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
