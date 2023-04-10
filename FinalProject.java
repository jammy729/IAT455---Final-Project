
/*
 * IAT 455 - Final Project
 * James Yoo: 301341943
 * Chelsea Irwawin Jennifer: 301384826
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author jamesyoo
 * 
 * 
 */

// TODO: 1. BRUSH RADII [1.5, 2.0] 
// TODO: 2. BRUSH LENGTH IN THE RANGE [4, 10]
// TODO: 3. HAVE CONSTRAINTS TO PREVENT LARGE BRUSH STROKES DOMINATING THE IMAGE
enum Parameter {
	RADIUS, LENGTH, THETA_RANGE
}

public class FinalProject extends Frame {
	private static final long serialVersionUID = 1L;

	Util util = new Util(); // utility functions
	JFileChooser fc = new JFileChooser();
	// image
	BufferedImage image;

	// SIZE
	int width;
	int height;

	// OPTIONS
	boolean DEBUG = false;
	boolean SET_THETA = true;
	boolean ANGLE_RANGE = false;
	boolean ORIENTATION = false;

	// SLIDER MIN AND MAX
	int min_slider_pixel = 4;
	int max_slider_pixel = 20;

	int min_slider_theta = 0;
	int max_slider_theta = 315;

	int min_slider_radius = 5;
	int max_slider_radius = 20;

	int min_slider_length = 4;
	int max_slider_length = 30;

	// SLIDER VALUE
	int pixel_gap = 7;
	int theta = 45; // angle

	public FinalProject() {
		loadImage();
		this.setTitle("Final Project - Impressionist Effect with Image Quilting technique");
		this.setVisible(true);
		RangeSliderWindow gui = new RangeSliderWindow(this);
		gui.display();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void loadImage() {
		try {
			if (!DEBUG) {
				fc.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					image = ImageIO.read(fc.getSelectedFile());
				} else {
					System.out.println("Command canccled by user");
				}
			} else {
				System.out.println("Debug On");
				image = ImageIO.read(new File("testImage.jpg"));
			}
			width = image.getWidth();
			height = image.getHeight();
		} catch (IOException e) {
			System.err.println("Error loading image: " + e.getMessage());
		}
	}

	/**
	 * Generates a random line of strokes for the image src
	 * 
	 * @param img: img to generate strokes for
	 * @return ArrayList of line objects
	 */
	public ArrayList<BrushStroke> generateStrokes(final BufferedImage src) {
		final ArrayList<BrushStroke> lines = new ArrayList<BrushStroke>();
		for (int x1 = 0; x1 < width; x1 += pixel_gap) {
			for (int y1 = 0; y1 < height; y1 += pixel_gap) {
				try {
					double random_length = util.randomValueBetween(min_slider_length, max_slider_length);
					float angle;
					float degrees;
					if (SET_THETA && !ANGLE_RANGE) {
						// user choose angle
						degrees = theta;
//						System.out.println("Set Angle: " + theta);
					} else if (!SET_THETA && ANGLE_RANGE) {
						// user change a min and max angle
						degrees = (float) util.randomValueBetween(min_slider_theta, max_slider_theta);
//						System.out.println("Angle Range: " + degrees);
					} else if (ORIENTATION) {
						// random angle
						degrees = (float) getAngle(src, x1, y1);
//						System.out.println("Random Angle: " + degrees);(float) util.randomValueBetween(min_slider_theta, max_slider_theta);
					} else {
						return lines;
					}
					angle = (float) Math.toRadians(degrees);

					// opposite = hypotenuse * sine(angle)
					int x2 = (int) (x1 + random_length * Math.sin(angle));
					// adhacebt = hypotenuse * cosine(angle)
					int y2 = (int) (y1 + random_length * Math.cos(angle));
					Color color = new Color(image.getRGB(x2, y2));

					lines.add(new BrushStroke(x1, y1, x2, y2, color));
				} catch (Exception e) {
					System.out.println("Error:" + e.getMessage());
				}
			}
		}
		return lines;
	}

	public void drawLines(final ArrayList<BrushStroke> lines, final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		for (BrushStroke line : lines) {
			int radius = (int) util.randomValueBetween(min_slider_radius, max_slider_radius);
			Stroke stroke = new BasicStroke(radius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2.setColor(line.color);
			g2.setStroke(stroke);
			g2.drawLine(line.x1, line.y1, line.x2, line.y2);
		}
	}

	public void min_max(Parameter param, int min, int max) {
		switch (param) {
		case RADIUS -> {
			min_slider_radius = min;
			max_slider_radius = max;
		}
		case LENGTH -> {
			min_slider_length = min;
			max_slider_length = max;
		}
		case THETA_RANGE -> {
			min_slider_theta = min;
			max_slider_theta = max;
		}
		default -> throw new IllegalArgumentException("Invalid Parameter: " + param);
		}
		repaint();
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

	public double getAngle(BufferedImage src, int x, int y) {
		int[][] surroundingPixels = new int[][] { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 }, { x - 1, y },
				{ x + 1, y }, { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

		Color pixelColor = new Color(src.getRGB(x, y));
		double lowestDistance = Double.MAX_VALUE;
		int closestPixelIndex = -1;

		for (int i = 0; i < surroundingPixels.length; i++) {
			int[] pixelPos = surroundingPixels[i];
			Color color = new Color(src.getRGB(pixelPos[0], pixelPos[1]));
			double distance = colorDistance(pixelColor, color);
			// keeps track of the surrounding pixel with the closest to the current pixel
			if (distance < lowestDistance) {
				lowestDistance = distance;
				closestPixelIndex = i;
			}
		}
		System.out.println(angle(closestPixelIndex));
		return angle(closestPixelIndex);
	}

	private static final double[] ANGLES = { 0, 45, 90, 135, 180, 225, 270, 315 };

	public double angle(int closestPixelIndex) {
		if (closestPixelIndex < 0 || closestPixelIndex >= ANGLES.length) {
			return 0;
		}
		return ANGLES[closestPixelIndex];
	}

	public void paint(Graphics g) {
		final ArrayList<BrushStroke> lines = generateStrokes(image);
		drawLines(lines, g);
		setSize(width, height);
	}

	public static void main(String[] args) {
		FinalProject finalProject = new FinalProject();
		finalProject.repaint();
	}
}