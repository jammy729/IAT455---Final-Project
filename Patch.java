import java.awt.image.BufferedImage;

public class Patch {
//	Handles shifting the patch and the patch itself
	private int width, height;
	BufferedImage image;
	
	public Patch(BufferedImage originalInput, int x, int y, int width, int height) {
		this.image = originalInput;
		this.width = width;
		this.height = height;
		setCorner(x,y);
	}
	
	
	
	protected void setCorner(int x, int y) {
		if(x + width > image.getWidth() | y + height > image.getHeight() | x < 0 | y < 0) {
			return;
		}
		setCorner(x, y);
	}
	
	
	
	protected int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}
}
