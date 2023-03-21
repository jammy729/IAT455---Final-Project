import java.awt.Color;
import java.awt.image.BufferedImage;

public enum Operations {
	add{

		@Override
		public int apply(BufferedImage src1, BufferedImage src2, int i, int j) {
			int rgb1 = src1.getRGB(i, j);
			int rgb2 = src2.getRGB(i, j);
			int r,g,b,rgb;
			r = getRed(rgb1)+ getRed(rgb2);
			r = clip(r);
			g = getGreen(rgb1)+ getGreen(rgb2);
			g = clip(g);
			b = getBlue(rgb1)+ getBlue(rgb2);
			b = clip(b);
			rgb = new Color(r,g,b).getRGB();
			return rgb;
		}
		
	}, 
	subtract{

		@Override
		public int apply(BufferedImage src1, BufferedImage src2, int i, int j) {
			int rgb1 = src1.getRGB(i, j);
			int rgb2 = src2.getRGB(i, j);
			int r,g,b,rgb;
			r = Math.abs(getRed(rgb1)- getRed(rgb2));
			r = clip(r);
			g = Math.abs(getGreen(rgb1)- getGreen(rgb2));
			g = clip(g);
			b = Math.abs(getBlue(rgb1)- getBlue(rgb2));
			b = clip(b);
			rgb = new Color(r,g,b).getRGB();
			return rgb;
		}
		
	},
	multiply {

		@Override
		public int apply(BufferedImage src1, BufferedImage src2, int i, int j) {
			int rgb1 = src1.getRGB(i, j);
			int rgb2 = src2.getRGB(i, j);
			int r,g,b,rgb;
			r = getRed(rgb1)*getRed(rgb2)/255;
			r = clip(r);
			g = getGreen(rgb1)* getGreen(rgb2)/255;
			g = clip(g);
			b = getBlue(rgb1)* getBlue(rgb2)/255;
			b = clip(b);
			rgb = new Color(r,g,b).getRGB();
			return rgb;
		}
	};
	
	public abstract int apply(BufferedImage src1, BufferedImage src2, int i, int j);
	
	private static int clip(int v) {
		v = v > 255 ? 255 : v;
		v = v < 0 ? 0 : v;
		return v;
	}

	protected int getRed(int pixel) {
		return (new Color(pixel)).getRed();
	}
	
	protected int getGreen(int pixel) {
		return (new Color(pixel)).getGreen();
	}

	protected int getBlue(int pixel) {
		return (new Color(pixel)).getBlue();
	}
}
