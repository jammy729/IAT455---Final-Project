
/*
 * IAT 455 - Final Project
 * James Yoo: 301341943
 * Chelsea Irwawin Jennifer: 301####
 */

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class FinalProject extends Frame {
	private static final long serialVersionUID = 1L;

	// image
	BufferedImage image;
	BufferedImage texturedImage;
	BufferedImage quilting;

	// size
	int width;
	int height;

	// screen size
	GraphicsDevice screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	int screenWidth = screenSize.getDisplayMode().getWidth();
	int screenHeight = screenSize.getDisplayMode().getHeight();

	// debug mode
	boolean debugMode = true;

	public FinalProject() {
		loadImages();
		texturedImage = impressionistEffect(image, 2);
		this.setTitle("Final Project - Impressionist Effect with Image Quilting technique");
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/*
	 * test mode - load default image from the local file !test mode - user can
	 * choose the image
	 */
	private void loadImages() {
		try {
			if (debugMode) {
				System.out.println("Debug Mode On");
				image = ImageIO.read(new File("testImage.jpg"));
			} else {
				System.out.println("Debug Mode off");
				JFileChooser chooser = new JFileChooser();
				// allow png, jpg
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					image = ImageIO.read(chooser.getSelectedFile());
				}
			}
			width = image.getWidth();
			height = image.getHeight();

		} catch (Exception e) {
			System.out.println("Cannot load the provided image");
		}
	}

	public BufferedImage impressionistEffect(BufferedImage src, int factor) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

		int width = src.getWidth();
		int height = src.getHeight();

		// apply the operation to each pixel
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = src.getRGB(i, j);
				int newR, newG, newB;

				newR = clip(getRed(rgb) * factor);
				newG = clip(getGreen(rgb) * factor);
				newB = clip(getBlue(rgb) * factor);

				rgb = new Color(newR, newG, newB).getRGB();
				result.setRGB(i, j, rgb);

			}
		}
		return result;
	}

	private int clip(int v) {
		v = v > 255 ? 255 : v;
		v = v < 0 ? 0 : v;
		return v;
	}

	protected int getRed(int pixel) {
		return (pixel >>> 16) & 0xFF;
	}

	protected int getGreen(int pixel) {
		return (pixel >>> 8) & 0xFF;
	}

	protected int getBlue(int pixel) {
		return pixel & 0xFF;
	}

	public void paint(Graphics g) {

		int w = screenWidth;
		int h = screenHeight;

		this.setSize(w, h);
		// resize by dividing the image width and height by multiplying a factor of 0.4
		g.drawImage(image, 0 + 10, 10, (int) (w * 0.5 - 10), (int) (h * 0.5 - 10), this);
		g.drawImage(texturedImage, 0 + 10, h / 2 + 10, w / 2 - 10, (int) (h * 0.5 - 10), this);

	}

	public static void main(String[] args) {
		FinalProject img = new FinalProject();
		img.repaint();

	}
}
