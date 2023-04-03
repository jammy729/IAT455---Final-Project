
public class MinPathFinder {
	private double costs[][];
	private TwoDLoc path[][];
	
	public MinPathFinder(double[][] dists, boolean allowHorizontal) {
		int rowCount = dists.length;
		int colCount = dists[0].length;
		path = new TwoDLoc[rowCount][colCount];
		costs = new double[rowCount][colCount];
		
		for(int i = 0; i < colCount; i++) {
		    costs[0][i] = dists[0][i];
		    path[0][i] = null;
		}
		
		
	}
	
	protected TwoDLoc follow(TwoDLoc currentLoc) {
		
	}
	
	protected TwoDLoc bestSourceLoc() {
		
	}
	
	protected double costOf(int row, int col) {
		
	}
	
	protected TwoDLoc[][] getPaths(){
		
	}
	
	protected double[][] getCosts(){
		
	}
}
