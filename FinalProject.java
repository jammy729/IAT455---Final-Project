
//IAT455 Final Project
/*
 * James Yoo: 301341943
 * Chelsea Irwawin Jennifer: 301####
 */
//**********************************************************/
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
class FinalProject extends Frame { // controlling class
	BufferedImage impressionistImage; // reference to an Image object
	BufferedImage quiltingImage; // mask for the painted areas
	BufferedImage originalImage;
	BufferedImage resultImage;

	int width; // width of the resized image
	int height; // height of the resized image

	public FinalProject() {
		// constructor
		// Get an image from the specified file in the current directory on the
		// local hard disk.
		try {
			originalImage = ImageIO.read(new File("car_cg.jpg")); // 1
			quiltingImage = ImageIO.read(new File("painted_areas_mask.jpg")); // 2

		} catch (Exception e) {
			System.out.println("Cannot load the provided image");
		}
		this.setTitle("Week 8 workshop");
		this.setVisible(true);

		width = originalImage.getWidth();//
		height = originalImage.getHeight();//

		// For images 5 to 12 you should replace the carImage with the proper methods
		// that perform the requested task

		// 5 Adding shadows to car
		impressionistImage = impressionistEffect(originalImage);
		quiltingImage = quiltingEffect(impressionistImage);

		// Anonymous inner-class listener to terminate program
		this.addWindowListener(new WindowAdapter() {// anonymous class definition
			public void windowClosing(WindowEvent e) {
				System.exit(0);// terminate the program
			}// end windowClosing()
		}// end WindowAdapter
		);// end addWindowListener
	}// end constructor

	public BufferedImage impressionistEffect(BufferedImage src) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

		int width = src.getWidth();
		int height = src.getHeight();

		// apply the operation to each pixel
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = src.getRGB(i, j);
				int newR, newG, newB;

				newR = getRed(rgb);
				newG = getGreen(rgb);
				newB = getBlue(rgb);

				// Decompose the image into a set of layers using Laplacian pyramid
				// decomposition
				// TODO: Implement Laplacian pyramid decomposition

				// Generate brushstrokes for each layer
				// TODO: Implement brushstroke generation

				// Mix the colors of the brushstrokes to create the final image
				// TODO: Implement color mixing

				rgb = new Color(newR, newG, newB).getRGB();
				result.setRGB(i, j, rgb);

			}
		}
		return result;
	}

	public BufferedImage quiltingEffect(BufferedImage src) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

		int width = src.getWidth();
		int height = src.getHeight();

		// apply the operation to each pixel
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = src.getRGB(i, j);
				int newR, newG, newB;

				// don't need to Math.abs as its 255 - n
				newR = (255 - getRed(rgb));
				newG = (255 - getGreen(rgb));
				newB = (255 - getBlue(rgb));

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
		int w = width / 5;
		int h = height / 5;

		this.setSize(w * 5 + 100, h * 4 + 50);

		g.drawImage(originalImage, 25, 50, w, h, this);
		g.drawImage(impressionistImage, 100 + w, 50, w, h, this);
		g.drawImage(quiltingImage, 125 + w, 50, w, h, this);
		g.drawImage(resultImage, 150 + w, 50, w, h, this);

		g.setColor(Color.BLACK);
		Font f1 = new Font("Verdana", Font.PLAIN, 13);
		g.setFont(f1);

		g.drawString("1.Original Image", 25, 40);
		g.drawString("2.Original img + Impressionist", 125 + w, 40);
		g.drawString("3.Impressionist img + quilting", 225 + w * 2, 40);

	}
	// =======================================================//

	public static void main(String[] args) {

		FinalProject img = new FinalProject();// instantiate this object
		img.repaint();// render the image

	}// end main
}
//=======================================================//