
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
	boolean DEBUG = true;
	boolean SET_THETA = true;
	boolean ANGLE_RANGE = false;

	// SLIDER MIN AND MAX
	int min_slider_pixel = 1;
	int max_slider_pixel = 10;

	int min_slider_theta = 0;
	int max_slider_theta = 315;

	int min_slider_radius = 5;
	int max_slider_radius = 20;

	int min_slider_length = 4;
	int max_slider_length = 10;

	// SLIDER VALUE
	int pixel_gap = 4;
	int theta = 45; // angle

	public FinalProject() {
		loadImages();
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

	public void loadImages() {
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
		for (int x1 = 0; x1 < width; x1 += 2) {
			for (int y1 = 0; y1 < height; y1 += 2) {
				try {
					double random_length = util.randomValueBetween(min_slider_length, max_slider_length);
					float angle;
					float degrees;
					if (SET_THETA && !ANGLE_RANGE) {
						// user choose angle
						degrees = (float) Math.toRadians(theta);
						System.out.println("Set Angle: " + theta);

					} else if (!SET_THETA && ANGLE_RANGE) {
						// user change a min and max angle
						degrees = (float) util.randomValueBetween(min_slider_theta, max_slider_theta);
						System.out.println("Angle Range: " + degrees);

					} else {
						// random angle
						degrees = (float) util.getAngle(src, x1, y1);
						System.out.println("Random Angle: " + degrees);

					}
					angle = (float) Math.toRadians(degrees);

					int x2 = (int) (x1 + random_length * Math.sin(angle));
					int y2 = (int) (y1 + random_length * Math.cos(angle));
					Color color = new Color(image.getRGB(x2, y2));

					lines.add(new BrushStroke(x1, y1, x2, y2, color));
				} catch (Exception e) {
					System.out.println("Error:" + e);
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