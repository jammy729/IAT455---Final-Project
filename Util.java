import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	protected int getRed(int pixel) {
		return (new Color(pixel)).getRed();
	}

	protected int getGreen(int pixel) {
		return (new Color(pixel)).getGreen();
	}

	protected int getBlue(int pixel) {
		return (new Color(pixel)).getBlue();
	}

	protected int clip(int v) {
		v = v > 255 ? 255 : v;
		v = v < 0 ? 0 : v;
		return v;
	}

	/**
	 * Generate a random double between min and max
	 * 
	 * "...asign random amounts to length and radius in ranges applied by the
	 * user.." in A.Random Perturbations
	 * 
	 * @param min: min the max value of the range
	 * @param max: max the max value of the range
	 * @return: random double value between min and max
	 */
	public double randomValueBetween(double min, double max) {
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

	/**
	 * https://www.compuphase.com/cmetric.htm Colour distance function given
	 * Calculate the color distance between two given colors
	 * 
	 * @param color1: the first color
	 * @param color2: the second color
	 * 
	 * @return: distance between two colors
	 */
	double colorDistance(Color color1, Color color2) {
		// calculate the difference
		int redDiff = color1.getRed() - color2.getRed();
		int greenDiff = color1.getGreen() - color2.getGreen();
		int blueDiff = color1.getBlue() - color2.getBlue();

		// mean level of red
		int redMean = (color1.getRed() + color2.getRed()) / 2;

		// formula
		int rMeanFactor = 512 + redMean;
		int rSquared = redDiff * redDiff;
		int gSquared = greenDiff * greenDiff;
		int bSquared = blueDiff * blueDiff;
		int bMeanFactor = 767 - redMean;

		return Math.sqrt((((rMeanFactor) * rSquared) >> 8) + 4 * gSquared + (((bMeanFactor) * bSquared) >> 8));
	}

	//
	public double getAngle(BufferedImage img, int x, int y) {
		int[][] surroundingPixels = new int[][] { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 }, { x - 1, y },
				{ x + 1, y }, { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

		// // set an initial lowest distance
		Color pixelColor = new Color(img.getRGB(x, y));
		double lowestDistance = colorDistance(pixelColor,
				new Color(img.getRGB(surroundingPixels[0][0], surroundingPixels[0][1])));
		int closestPixelIndex = 0;

		// for each one of the surrounding pixels,
		for (int i = 1; i < surroundingPixels.length; i++) {
			double distance = colorDistance(pixelColor,
					new Color(img.getRGB(surroundingPixels[i][0], surroundingPixels[i][1])));
			// if this pixel's distance is smaller than the current lowest distance...
			if (distance < lowestDistance) {
				lowestDistance = distance;
				closestPixelIndex = i;
			}
		}
		System.out.println(getAngle(closestPixelIndex));
		return getAngle(closestPixelIndex);
		// return closestPixelIndex;
	}

//	0, 45, 90, 135, 180, 225, 270, 315
	public double getAngle(int index) {
		switch (index) {
		case 0:
			return 0;
		case 1:
			return 45;
		case 2:
			return 90;
		case 3:
			return 135;
		case 4:
			return 180;
		case 5:
			return 225;
		case 6:
			return 270;
		case 7:
			return 315;
		default:
			return 0;
		}

//		case 0:
//			return -135;
//		case 1:
//			return 180;
//		case 2:
//			return 135;
//		case 3:
//			return -90;
//		case 4:
//			return 90;
//		case 5:
//			return -45;
//		case 6:
//			return 0;
//		case 7:
//			return 45;
//		default:
//			return 0;
//		}
	}

}
