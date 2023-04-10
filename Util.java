import java.awt.Color;
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
	 * https://www.educative.io/answers/how-to-generate-random-numbers-in-java
	 * https://www.educative.io/answers/how-to-generate-random-numbers-in-java
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

}
