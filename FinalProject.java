
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

enum Parameter {
	RADIUS, LENGTH, ANGLE
}

class FinalProject extends Frame {
	private static final long serialVersionUID = 1L;

	Util util = new Util(); // utility functions

	// image
	BufferedImage image;

	// size
	int width;
	int height;

	// debug mode
	boolean debug = true;

	// slider min and max
	int min_slider_interval = 1;
	int max_slider_interval = 10;
	int min_slider_radius = 1;
	int max_slider_radius = 10;
	int min_slider_length = 1;
	int max_slider_length = 20;

	// default slider value
	int pixel_gap = 3;
	int min_radius = 2;
	int max_radius = 8;
	int min_length = 4;
	int max_length = 17;

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
			if (!debug) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					image = ImageIO.read(chooser.getSelectedFile());
				} else {
					return;
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

		/**
		 * pixel_gap a feature???
		 */
		for (int x1 = 0; x1 < width; x1 = x1 + pixel_gap) {
			for (int y1 = 0; y1 < height; y1 = y1 + (pixel_gap)) {
				try {

					// (30*r + 59*g + 11*b)/100 from the article
					int rgb = src.getRGB(x1, y1);
					int newR, newG, newB, intensity;

					newR = util.clip(util.getRed(rgb) * 30);
					newG = util.clip(util.getGreen(rgb) * 59);
					newB = util.clip(util.getBlue(rgb) * 11);
					intensity = util.clip((newR + newG + newB) / 100);

					double random_length = util.randomDoubleBetween(min_length, max_length);
					/**
					 * not sure what's going on with the angles. just playing around with the
					 * intensity formula I found on the paper
					 * 
					 */
					/**
					 * TODO: MAYBE GIVE FEW OPTIONS FOR SEVERAL ANGLES AS UI CONTROL OR RANDOMNESS?
					 */
					float angle = (float) Math.toRadians((x1 / intensity) * random_length);
					int x2 = (int) (x1 + random_length * Math.sin(angle));
					/**
					 * TODO: not sure why sin on y2 just creates strai
					 */
					int y2 = (int) (y1 + random_length * Math.cos(angle));
					Color color = new Color(image.getRGB(x2, y2));
					System.out.println(angle);
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
			int strokeRadius = (int) util.randomDoubleBetween(min_radius, max_radius);
			/**
			 * TODO: MAYBE HAVE THE STROKE ENDPOINT TO BE UI CONTROLLED??
			 */
			System.out.println("x1:" + line.x1);
			System.out.println("y1:" + line.y1);
			System.out.println("x2:" + line.x2);
			System.out.println("y2:" + line.y2);
			System.out.println("color:" + line.color);

			Stroke stroke = new BasicStroke(strokeRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2.setColor(line.color);
			g2.setStroke(stroke);
			g2.drawLine(line.x1, line.y1, line.x2, line.y2);
		}
	}

	/**
	 * TODO: add angle?
	 * 
	 * @param param
	 * @param min
	 * @param max
	 */
	public void min_max(Parameter param, int min, int max) {
		switch (param) {
		case RADIUS -> {
			min_radius = min;
			max_radius = max;
		}
		case LENGTH -> {
			min_length = min;
			max_length = max;
		}
		default -> throw new IllegalArgumentException("Invalid parameter: " + param);
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
		return;
////		img.repaint();
	}

}