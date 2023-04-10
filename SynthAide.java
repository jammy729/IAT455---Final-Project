import java.util.LinkedList;

public class SynthAide {
	public static void copy(View from, View to, int firstX, int firstY, int width, int height) {
		int finalX = firstX + width - 1;
		int finalY = firstY + height - 1;
		
		for(int i = firstX; i <= finalX ; i++) {
			for(int j = firstY; j <= finalY; j++) {
				to.putSample(i, j, from.getSample(i, j));
			}
		}
	}
	
	public static double[][] gaussian (int length){
		if( length % 2 == 0 )
		    length++;

	
	// this stddev puts makes a good spread for a given size
		double stddev = length / 4.9;

		// one dimensional gaussian kernel
		double oned[] = new double[length];
		for(int i = 0; i < length; i++) {
		    int x = i - length/2;
		    double exponent = x*x / (-2 * stddev * stddev);
		    oned[i] = Math.exp(exponent);
		}

		// two dimensional gaussian kernel based on the first one
		double twod[][] = new double[length][length];
		double sum = 0.0;
		for(int i = 0; i < length; i++) {
		    for(int j = 0; j < length; j++) {
			twod[i][j] = oned[i] * oned[j];
			sum += twod[i][j];
		    }
		}

		// normalize
		for(int i = 0; i < length; i++) {
		    for(int j = 0; j < length; j++) {
			twod[i][j] /= sum;
		    }
		}

		return twod;
	    }

	    
	    public static LinkedList lessThanEqual(double[][] vals, double threshold) {

		LinkedList list = new LinkedList();
		for(int r = 0; r < vals.length; r++) {
		    for(int c = 0; c < vals[r].length; c++) {
			if( vals[r][c] >= 0 && vals[r][c] <= threshold ) {
			    list.addFirst( new TwoDLoc(r,c) );
			}
		    }
		}
		return list;
	    }

	   
	    public static void blend(Patch fromPatch, Patch toPatch, int x, int y,
				     double frompart) {

		int[] tovals = toPatch.getSample(x,y);
		int[] fromvals = fromPatch.getSample(x,y);
		int[] newvals = new int[3];
		for(int i = 0; i < 3; i++) {
		    double sum = tovals[i]*(1-frompart) + fromvals[i]*frompart;
		    newvals[i] = (int) Math.round( sum );
		}
		toPatch.putSample(x,y,newvals);
	    }


	    public static int ssd(View view1, View view2, int x, int y) {

		int vals[] = view1.getSample(x, y);
		int vals2[] = view2.getSample(x, y);
		
		int diff = vals[0] - vals2[0];
		int sum = diff * diff;
		diff = vals[1] - vals2[1];
		sum += diff * diff;
		diff = vals[2] - vals2[2];
		sum += diff * diff;

		return sum;
	    }

}
