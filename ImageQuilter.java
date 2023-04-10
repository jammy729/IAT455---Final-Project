import java.awt.image.BufferedImage;

public class ImageQuilter {
	BufferedImage image;
	private int patchSize;
	private int overlapSize;
	
	private boolean horizontalPaths;
	private double pathCost;
	
	public ImageQuilter(BufferedImage image, int patchSize, int overlapSize, boolean horizontalPaths, double pathCost) {
		this.overlapSize = overlapSize;
		this.patchSize = patchSize;
		this.image = image;
		this.horizontalPaths = horizontalPaths;
		this.pathCost = pathCost;
	}
}
