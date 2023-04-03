import java.awt.image.BufferedImage;

public class Patch extends View{
//	Handles shifting the patch and the patch itself
	private int width, height;
	BufferedImage image;
	
	public Patch(BufferedImage originalInput, int x, int y, int width, int height) {
		super(originalInput, x, y);
		this.image = originalInput;
		this.width = width;
		this.height = height;
		setCorner(x,y);
	}
	
	
	
	protected void setCorner(int x, int y) {
		if(x + width > image.getWidth() | y + height > image.getHeight() | x < 0 | y < 0) {
			return;
		}
		super.setCorner(x, y);
	}
	
	
	
	protected int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}
	
	protected int[] getSample(int x, int y, int[] out) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			return super.getSample(x, y);
		}
		throw new IllegalArgumentException("Attempts to get ("+x+","+y
				   +") from "+toString() + "is invalid.");	
	}
	
	protected void putSample(int x, int y, int[] values) {
		if (x >=0 && x < width && y >= 0 && y < height) {
			super.putSample(x, y, values);
		}
		throw new IllegalArgumentException("Attempts to put ("+x+","+y+") from "+toString() + "is invalid.");
	}
	
	protected boolean rightOnePixel() {
		if (xOffset + width  < image.getWidth()) {
			setCorner(xOffset + 1, yOffset);
			return true;
		}
		return false;
	}
	
	protected boolean nextPixelRow() {
		if(yOffset + height < image.getHeight() ) {
			setCorner(0, yOffset + 1);
			return true;
		}
		return false;
	}
	
	protected boolean nextCol(int over) {
		if(xOffset + width - over < 0 || xOffset + width - over > image.getWidth()) {
			return false;
		}
		setCorner(xOffset + width - over, yOffset);
		return true;
	}
	
	protected boolean nextRow(int over) {
		if(yOffset + height - over < 0 || yOffset + height - over > image.getHeight()) {
			return false;
		}
		setCorner(0, yOffset);
		return true;
	}
	
	protected int getXOffset() {
		return xOffset;
	}
	
	protected int getYOffset() {
		return yOffset;
	}
}
