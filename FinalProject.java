
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

	int minAllowedPixelInterval = 1; // minimum allowed pixels between strokes
	int maxAllowedPixelInterval = 10; // maximum allowed pixels between strokes
	int minAllowedStrokeRadius = 1; // minimum allowed stroke radius
	int maxAllowedStrokeRadius = 16; // maximum allowed stroke radius
	int minAllowedStrokeLength = 1; // minimum allowed stroke length
	int maxAllowedStrokeLength = 20; // maximum allowed stroke length
	int minAllowedStrokeAngle = 0; // minimum allowed stroke angle
	int maxAllowedStrokeAngle = 360; // maximum allowed stroke angle

	int minStrokeRadius = 1;
	int maxStrokeRadius = 12;
	int minStrokeLength = 4;
	int maxStrokeLength = 20;
	int minStrokeAngle = 30;
	int pixelInterval = 10;

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
				// allow png, jpg
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					image = ImageIO.read(chooser.getSelectedFile());
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

		double angleInDegrees;
		float angle;

		for (int i = 0; i < width; i = i + pixelInterval) {
			for (int j = 0; j < height; j = j + pixelInterval) {
				try {
					final double length = util.randomDoubleBetween(minStrokeLength, maxStrokeLength);

					angleInDegrees = util.getOrientationForPixel(src, i, j);
					angle = (float) Math.toRadians(angleInDegrees);

					final int endX = (int) (i + length * Math.sin(angle));
					final int endY = (int) (j + length * Math.cos(angle));

					Color color = new Color(image.getRGB(i, j));

					lines.add(new Line(i, j, endX, endY, color));
				} catch (final Exception e) {
					System.out.println(e);
				}
			}
		}
		return lines;
	}

	private void drawLines(final ArrayList<Line> lines, final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		for (final Line line : lines) {
			final int strokeRadius = (int) util.randomDoubleBetween(minStrokeRadius, maxStrokeRadius);
			final Stroke stroke = new BasicStroke(strokeRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			final int x1 = line.x1;
			final int y1 = line.y1;
			final int x2 = line.x2;
			final int y2 = line.y2;
			g2.setColor(line.color);
			g2.setStroke(stroke);
			g2.drawLine(x1, y1, x2, y2);
		}
	}

	public void setRange(Parameter param, int min, int max) {
		switch (param) {
		case radius:
			this.minStrokeRadius = min;
			this.maxStrokeRadius = max;
			break;
		case length:
			this.minStrokeLength = min;
			this.maxStrokeLength = max;
			break;
		default:
			break;
		}
		repaint();
	}

	public void paint(Graphics g) {
		final ArrayList<Line> lines = generateStrokes(image);
		Util.shuffle(lines);
		drawLines(lines, g);
		this.setSize(width, height);
	}

	public static void main(String[] args) {
		FinalProject img = new FinalProject();
		img.repaint();
	}

}