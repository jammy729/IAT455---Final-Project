
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
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class FinalProject extends Frame {
	private static final long serialVersionUID = 1L;
	Util util = new Util(); // utility functions
	List<Line2D.Double> lines; // holds the list of generated 'strokes'

	BufferedImage image;
	BufferedImage texturedImage;
	BufferedImage quilting;
	// size
	int width;
	int height;

	// debug mode
	boolean debugMode = true;

	// slider min and max
	int minAllowedPixelInterval = 1;
	int maxAllowedPixelInterval = 10;
	int minAllowedStrokeRadius = 1;
	int maxAllowedStrokeRadius = 5;
	int minAllowedStrokeLength = 4;
	int maxAllowedStrokeLength = 20;
	int minAllowedStrokeAngle = 0;
	int maxAllowedStrokeAngle = 360;

	// default slider value
	int minStrokeRadius = 1;
	int maxStrokeRadius = 5;
	int minStrokeLength = 4;
	int maxStrokeLength = 20;
	int pixelInterval = 3;
	int minStrokeAngle = 30; // minimum stroke angle (if isOrientationByGradient is false)
	int maxStrokeAngle = 60; // maximum stroke angle (if isOrientationByGradient is false)

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

	private void loadImages() {
		try {
			if (!debugMode) {
				System.out.println("Debug Mode off");
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					image = ImageIO.read(chooser.getSelectedFile());
				} else {
					return;
				}

			} else {
				System.out.println("Debug Mode On");
				image = ImageIO.read(new File("testImage.jpg"));
			}
			width = image.getWidth();
			height = image.getHeight();

		} catch (Exception e) {
			System.out.println("Cannot load the provided image");
		}
	}

	/**
	 * Generates a random line of strokes for the image src
	 * 
	 * @param img: img to generate strokes for
	 * @return ArrayList of line objects
	 */
	private ArrayList<Line> generateStrokes(final BufferedImage src) {

		final ArrayList<Line> lines = new ArrayList<Line>();

		for (int i = 0; i < width; i = i + pixelInterval) {
			for (int j = 0; j < height; j = j + pixelInterval) {
				try {
					double randomStrokeLength = util.randomDoubleBetween(minStrokeLength, maxStrokeLength);

					double angleInDegrees = util.getOrientationForPixel(src, i, j);
					float angle = (float) Math.toRadians(angleInDegrees);

					int endPointX = (int) (i + randomStrokeLength * Math.sin(angle));
					int endPointY = (int) (j + randomStrokeLength * Math.cos(angle));

					Color color = new Color(image.getRGB(i, j));

					lines.add(new Line(i, j, endPointX, endPointY, color));
				} catch (final Exception e) {
					System.out.println(e);
				}
			}
		}
		return lines;
	}

	private void drawLines(final ArrayList<Line> lines, final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		for (Line line : lines) {
			final int strokeRadius = (int) util.randomDoubleBetween(minStrokeRadius, maxStrokeRadius);
			final Stroke stroke = new BasicStroke(strokeRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2.setColor(line.color);
			g2.setStroke(stroke);
			g2.drawLine(line.x1, line.y1, line.x2, line.y2);
		}
	}

	public void setRange(Parameter param, int min, int max) {
		switch (param) {
		case radius -> {
			minStrokeRadius = min;
			maxStrokeRadius = max;
		}
		case length -> {
			minStrokeLength = min;
			maxStrokeLength = max;
		}
		default -> throw new IllegalArgumentException("Invalid parameter: " + param);
		}
		repaint();
	}

	public void paint(Graphics g) {
		final ArrayList<Line> lines = generateStrokes(image);
		Util.shuffle(lines);
		drawLines(lines, g);
		setSize(width, height);
	}

	public static void main(String[] args) {
		FinalProject img = new FinalProject();
		img.repaint();
	}

}