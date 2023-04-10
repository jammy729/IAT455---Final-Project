
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
		
		if(colCount == 1) {
			for(int i = 1; i< rowCount; i++ ) {
				costs[i][0] = costs[i-1][0] + dists[i][0];
				path[i][0] = new TwoDLoc(i-1, 0);
			}
			return;
		}
		
		for(int i = 0; i < rowCount; i++) {
			//Farthest left column
			int choice;
			if(costs[i][0] < costs[i][1]) {
				choice = 0;
			}
			else {
				choice = 1;
			}
			costs[i+1][0] = dists[i+1][0] + costs[i][choice];
			path[i+1][0] = new TwoDLoc(i, choice);
			
			//Mid column
			for (int j = 1; j < colCount - 1; j ++)
			{
				//checking to the left of the column
				if(costs[i][j-1] < costs[i][j]) {
					choice = j-1;
				}
				else {
					choice = j;
				}
				
				//checking to the right of the column
				if(costs[i][j+1] < costs[i][choice]) {
					choice = j+1;
				}
				
				costs[i+1][j] = dists[i+1][j] + costs[i][choice];
				path[i+1][j] = new TwoDLoc(i, choice);
			}			
			
			//Farthest right column
			int j = colCount - 1;
			if(costs[i][j] < costs[i][j-1]) {
				choice = j;
			}
			else {
				choice = j-1;
			}
			costs[i+1][j] = dists[i+1][j] + costs[i][choice];
			path[i+1][j] = new TwoDLoc(i, choice);
			
			if(allowHorizontal) {
				horizontalMovement(dists[i+1], i+1);
			}
		}
	}
	
	protected TwoDLoc follow(TwoDLoc currentLoc) {
		return path[currentLoc.getRow()][currentLoc.getCol()];
	}
	
	protected TwoDLoc bestSourceLoc() {
		int j = 0;
		for(int i = 1; i < costs[0].length; i++) {
			if(costs[costs.length-1][i] < costs[costs.length-1][j]) {
				j = i;
			}
		}
		return new TwoDLoc(costs.length-1, j);
	}
	
	protected double costOf(int row, int col) {
		return costs[row][col];
	}
	
	protected TwoDLoc[][] getPaths(){
		return path;
	}
	
	protected double[][] getCosts(){
		return costs;
	}
	
	private void horizontalMovement(double[] dists, int row) {
		boolean dontChange;
		
		do {
			dontChange = false;
			
			int j = 0;
			int choice = j + 1;
			double newCost = costs[row][choice] + dists[j];
			if(costs[row][j] > newCost) {
				dontChange = true;
				costs[row][j] = newCost;
				path[row][j] = new TwoDLoc(row, choice);
			}
			
			for ( j = 1; j < dists.length - 1; j++) {
				if(costs[row][j-1] > costs[row][j+1]) {
					choice = j+1;
				}
				else {
					choice = j-1;
				}
				
				newCost = costs[row][choice] + dists[j];
				if(costs[row][j] > newCost) {
					dontChange = true;
					costs[row][j] = newCost;
					path[row][j] = new TwoDLoc(row, choice);
				}
			}
			
			j = dists.length - 1;
			choice = j-1;
			newCost = costs[row][choice] + dists[j];
			if(costs[row][j] > newCost) {
				dontChange = true;
				costs[row][j] = newCost;
				path[row][j] = new TwoDLoc(row, choice);
			}
			
			
		} while(dontChange);
	}
}
