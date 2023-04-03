
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
		
	}
	
	public static void blend(Patch fromPatch, Patch toPatch, int x, int y,double frompart) {
		
	}
	
	public static int ssd(View view1,View view2, int x, int y) {
		
	}
}
