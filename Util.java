import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	/**
	 * Generate a random double between min and max
	 * 
	 * @param min: min the max value of the range
	 * @param max: max the max value of the range
	 * @return: random double value between min and max
	 */
	public double randomDoubleBetween(double min, double max) {
		Random random = new Random();
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

	/**
	 * Shuffles the elements in the given ArrayList in random order
	 * 
	 * @param list the ArrayList to shuffle
	 */
	public static <T> void shuffle(ArrayList<T> list) {
		Collections.shuffle(list);
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

	public double getOrientationForPixel(BufferedImage img, int x, int y) {
		int[][] surroundingPixels = new int[][] { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 }, { x - 1, y },
				{ x + 1, y }, { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

		// set an initial lowest distance
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

		double angle = 0;
		switch (closestPixelIndex) {
		case 0:
			angle = -135;
			break;
		case 1:
			angle = 180;
			break;
		case 2:
			angle = 135;
			break;
		case 3:
			angle = -90;
			break;
		case 4:
			angle = 90;
			break;
		case 5:
			angle = -45;
			break;
		case 6:
			angle = 0;
			break;
		case 7:
			angle = 45;
			break;
		default:
			break;
		}
		return angle;
	}
}
