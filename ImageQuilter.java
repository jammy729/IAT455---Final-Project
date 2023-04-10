import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class ImageQuilter {
	BufferedImage image;
	private int patchSize;
	private int overlapSize;
	
	private boolean horizontalPaths;
	private double pathCost;
	
	public ImageQuilter(BufferedImage image, int patchSize, int overlapSize, boolean horizontalPaths, double pathCost) {
		this.overlapSize = overlapSize;
		this.patchSize = patchSize;
		this.image = image;
		this.horizontalPaths = horizontalPaths;
		this.pathCost = pathCost;
	}
	
	public BufferedImage synthesize(int outputWidth, int outputHeight) {
		if(outputWidth < patchSize || outputHeight < patchSize) {
			throw new IllegalArgumentException("Output size too small. try again");
		}
		
		int patchColumns = Math.round((float)(outputWidth - patchSize) / (patchSize - overlapSize));
		int patchRows = Math.round((float)(outputHeight - patchSize) / (patchSize - overlapSize));
		
		int newWidth = patchColumns * (patchSize - overlapSize) + patchSize;
		int newHeight = patchRows * (patchSize - overlapSize) + patchSize;
		patchColumns++;
		patchRows++;
		if(newWidth != outputWidth || newHeight != outputHeight) {
			outputWidth = newWidth;
			outputHeight = newHeight;
		}
		
		BufferedImage out = null;
		out = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_RGB);
		
		int x = (int) (Math.random() * (image.getWidth() - patchSize));
		int y = (int) (Math.random() * (image.getHeight() - patchSize));
		
		View view = new View(image, x, y);
		Patch outputPatch = new Patch(out, 0, 0, patchSize, patchSize);
		SynthAide.copy(view,  outputPatch, 0, 0, patchSize, patchSize);
		
		if(!outputPatch.nextCol(overlapSize)) return out;
		
		int currentRow = 0;
		double dists[][] = new double[image.getHeight()-patchSize+1][image.getWidth()-patchSize+1];
		do {
			do {
				TwoDLoc bestLocation = calcDistance(dists, outputPatch);
				double bestValue = dists[bestLocation.getRow()][bestLocation.getCol()];
				
				double threshold = bestValue * 1.1;
				LinkedList localList = SynthAide.lessThanEqual(dists, threshold);
				
				int choice = (int) (Math.random() * localList.size());
				TwoDLoc locate = (TwoDLoc) localList.get(choice);
				
				pathAndFill(outputPatch, locate);
			} while(outputPatch.nextCol(overlapSize));
			currentRow++;
		} while(outputPatch.nextRow(overlapSize));
		return out;
	}
	
	private TwoDLoc calcDistance(double[][] dists, Patch outputPatch) {
		double bestValue = Double.MAX_VALUE;
		TwoDLoc bestLocation = null;
		
		Patch patch = new Patch(image, 0, 0, patchSize, patchSize);
		do {
			do {
				double sum = 0.0;
				double leftOver[][] = null;
				double topOver[][] = null;
				int count = 0;
				
				if(!outputPatch.isAtLeftEdge()) {
					leftOver = getLeftOverlapDistance(outputPatch, patch);
					for(int i = 0; i < leftOver.length; i++) {
						for(int j = 0; j< leftOver[i].length; j++) {
							sum += leftOver[i][j];
						}
					}
					count += leftOver.length * leftOver[0].length;
				}
				
				if(!outputPatch.isAtTopEdge()) {
					leftOver = getTopOverlapDistance(outputPatch, patch);
					for(int i = 0; i < topOver.length; i++) {
						for(int j = 0; j< topOver[i].length; j++) {
							sum += topOver[i][j];
						}
					}
					count += topOver.length * topOver[0].length;
				}
				
				if(topOver != null && leftOver != null) {
					for(int i = 0; i < overlapSize; i++) {
						for(int j = 0; j < overlapSize; j++) {
							sum -= SynthAide.ssd(outputPatch, patch, i, j) / 3.0;
						}
					}
					count -= overlapSize * overlapSize;
				}
				
				sum = sum / (count * 255 * 255);
				
				if(pathCost > 0) {
					double costs = averageCost(leftOver, topOver);
					
					costs = costs / (255* 255);
					sum = sum * (1-pathCost) + pathCost * costs;
				}
				
				int x = patch.getCornerX();
				int y = patch.getCornerY();
				dists[y][x] = sum;
				if(sum < bestValue) {
					bestValue = sum;
					bestLocation = new TwoDLoc(y,x);
				}
			} while (patch.rightOnePixel());
		} while(patch.nextPixelRow());
		return bestLocation;
	}
	
	private double averageCost(double [][] leftOver, double [][] topOver) {
		double costs;
		int rowCount;
		
		if(leftOver == null) {
			MinPathFinder topPath = new MinPathFinder(topOver, horizontalPaths);
			
			TwoDLoc locate = topPath.bestSourceLoc();
			costs = topPath.costOf(locate.getRow(), locate.getCol());
			rowCount = patchSize;
		}
		
		else if(topOver == null) {
			MinPathFinder leftPath = new MinPathFinder(leftOver, horizontalPaths);
			
			TwoDLoc locate = leftPath.bestSourceLoc();
			costs = leftPath.costOf(locate.getRow(), locate.getCol());
			rowCount = patchSize;
		}
		
		else {
			MinPathFinder leftPath = new MinPathFinder(leftOver, horizontalPaths);
			MinPathFinder topPath = new MinPathFinder(topOver, horizontalPaths);
			
			TwoDLoc leftLocation = new TwoDLoc(0,0);
			TwoDLoc topLocation = new TwoDLoc(0,0);
			
			choosePathIntersection(leftPath, topPath, leftLocation, topLocation);
			
			costs = leftPath.costOf(leftLocation.getRow(), leftLocation.getCol());
			costs += topPath.costOf(topLocation.getRow(), topLocation.getCol());
			
			rowCount = 2* patchSize - 2 - leftLocation.getRow() - topLocation.getRow();
			rowCount = 2* patchSize - rowCount;
		}
		
		return costs / rowCount;
	}
	
	private double [][] getLeftOverlapDistance(Patch outputPatch, Patch patch){
		int rowCount = outputPatch.getHeight();
		double dists[][] = new double [rowCount][overlapSize];
		int arrayRow = rowCount - 1;
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < overlapSize; j++) {
				dists[arrayRow][j] = SynthAide.ssd(outputPatch,  patch,  j, i) / 3;
			}
			arrayRow--;
		}
		return dists;
	}
	
	private double [][] getTopOverlapDistance(Patch outputPatch, Patch patch){
		int rowCount = outputPatch.getWidth();
		double dists[][] = new double [rowCount][overlapSize];
		int arrayRow = rowCount - 1;
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < overlapSize; j++) {
				dists[arrayRow][j] = SynthAide.ssd(outputPatch,  patch,  i, j) / 3;
			}
		}
		return dists;
	}
	
	private void fillAndBlend(Patch outputPatch, TwoDLoc locate) {
		Patch patch = new Patch(image, locate.getX(), locate.getY(), patchSize, patchSize);
		if(outputPatch.isAtTopEdge() ) {
			for(int i = 0; i < patchSize; i++) {
				for(int j = 0; j < overlapSize; j++) {
				    double inpart = (double) j / overlapSize;
				    SynthAide.blend(patch, outputPatch, j, i, inpart);
				}
		    }
		    SynthAide.copy(patch, outputPatch, overlapSize,
				   0, patchSize-overlapSize, overlapSize);
		}

		else if( outputPatch.isAtLeftEdge() ) {
		    
		    //area on top
		    for(int c = 0; c < patchSize; c++) {
				for(int r = 0; r < overlapSize; r++) {
				    double inpart = (double) r / overlapSize;
				    SynthAide.blend(patch, outputPatch, c, r, inpart);
				}
		    }
		    SynthAide.copy(patch, outputPatch, 0, overlapSize,
				   overlapSize, patchSize-overlapSize);
		}
		
		else {

		    // area on top
		    for(int c = overlapSize; c < patchSize; c++) {
			for(int r = 0; r < overlapSize; r++) {
			    double inpart = (double) r / overlapSize;
			    SynthAide.blend(patch, outputPatch, c, r, inpart);
			}
		    }
		    
		    // area on the left
		    for(int r = overlapSize; r < patchSize; r++) {
			for(int c = 0; c < overlapSize; c++) {
			    double inpart = (double) c / overlapSize;
			    SynthAide.blend(patch, outputPatch, c, r, inpart);
			}
		    }

		    //combined overlap
		    for(int r = 0; r < overlapSize; r++) {
			for(int c = 0; c < overlapSize; c++) {
			    double inpart = (double)c*r/(overlapSize*overlapSize);
			    SynthAide.blend(patch, outputPatch, c, r, inpart);
			}
		    }
		}
		int size = patchSize - overlapSize;
		SynthAide.copy(patch, outputPatch, overlapSize, overlapSize, size,size);
	    }

	    private void pathAndFill(Patch outputPatch, TwoDLoc loc) {

		boolean allow = horizontalPaths;
		Patch patch = new Patch(image, loc.getX(), loc.getY(),
					  patchSize, patchSize);

		if( outputPatch.isAtLeftEdge() ) {

		    SynthAide.copy(patch, outputPatch, 0, 0, overlapSize, patchSize);
		    double[][] topOverlap = getTopOverlapDistance(outputPatch, patch);
		    MinPathFinder topFinder = new MinPathFinder(topOverlap, allow);
		    TwoDLoc source = topFinder.bestSourceLoc();
		    followTopOverlapPath(outputPatch, patch, topFinder, source);
		}

		else if( outputPatch.isAtTopEdge() ) {

		    SynthAide.copy(patch, outputPatch, 0, 0, patchSize, overlapSize);
		    double[][] leftOverlap = getLeftOverlapDistance(outputPatch, patch);
		    MinPathFinder leftFinder = new MinPathFinder(leftOverlap, allow);
		    TwoDLoc source = leftFinder.bestSourceLoc();
		    followLeftOverlapPath(outputPatch, patch, leftFinder, source);
		}

		else {

		    double[][] topOverlap = getTopOverlapDistance(outputPatch, patch);
		    double[][] leftOverlap = getLeftOverlapDistance(outputPatch, patch);
		    MinPathFinder topFinder = new MinPathFinder(topOverlap, allow);
		    MinPathFinder leftFinder = new MinPathFinder(leftOverlap, allow);
		    TwoDLoc leftloc = new TwoDLoc(0,0);
		    TwoDLoc toploc = new TwoDLoc(0,0);

		    choosePathIntersection(leftFinder, topFinder, leftloc, toploc);

		   //Find first pixel
		    boolean firstPixel[][] = new boolean[overlapSize][overlapSize];

		    // left overlap
		    while( leftloc.getRow() < overlapSize ) {
			int r = leftloc.getRow();
			for(int c = leftloc.getCol(); c < overlapSize; c++) {
			    firstPixel[r][c] = true;
			}
			leftloc = leftFinder.follow(leftloc);
		    }

		    // top and left overlap
		    while( toploc.getRow() < overlapSize ) {
			int r = toploc.getRow();
			for(int c = 0; c < overlapSize; c++) {
			    firstPixel[c][r] = firstPixel[c][r] && c >= toploc.getCol();
			}
			toploc = topFinder.follow(toploc);
		    }

		    //fill in the corners
		    for(int r = 0; r < overlapSize; r++) {
			for(int c = 0; c < overlapSize; c++) {
			    if( firstPixel[r][c] ) {
				outputPatch.putSample( c, r, patch.getSample(c,r) );
			    }
			}
		    }

		    // handle the rest of the overlap regions
		    followLeftOverlapPath(outputPatch, patch, leftFinder, leftloc);
		    followTopOverlapPath(outputPatch, patch, topFinder, toploc);
		}

		// fill in the non-overlap area
		int size = patchSize - overlapSize;
		SynthAide.copy(patch, outputPatch, overlapSize, overlapSize, size,size);
	    }

			    
	    private void followLeftOverlapPath(Patch toPatch, Patch fromPatch,
					 MinPathFinder finder, TwoDLoc source) {

		// loop until we reach the destination
		while( source != null ) {

		    int y = patchSize - source.getRow() - 1;
		    int x = source.getCol();

		    // values to the right of x are filled in from fromPatch
		    for(x++; x < overlapSize; x++) {
			toPatch.putSample(x, y, fromPatch.getSample(x, y));
		    }

		    // values at that low point are averaged
		    x = source.getCol();
		    SynthAide.blend(fromPatch, toPatch, x, y, 0.5);
		    
		    
		    int oldrow = source.getRow();
		    do{
		    	source = finder.follow(source);
		    } while( source != null && source.getRow() == oldrow );
		}

	    }

	    
	    private void followTopOverlapPath(Patch toPatch, Patch fromPatch,
					 MinPathFinder finder, TwoDLoc source) {

		// loop until we reach the destination
		while( source != null ) {

		    int x = patchSize - source.getRow() - 1;
		    int y = source.getCol();

		    // values below y are filled in from the old patch
		    for(y++; y < overlapSize; y++) {
			toPatch.putSample(x, y, fromPatch.getSample(x, y));
		    }

		    // values at that low point are averaged
		    y = source.getCol();
		    SynthAide.blend(fromPatch, toPatch, x, y, 0.5);
		   
//		    Check all the pixels
		    int oldrow = source.getRow();
		    do{
			source = finder.follow(source);
		    } while( source != null && source.getRow() == oldrow );
		}

	    }

//	    Finding intersection point between left location and top location
	    private void choosePathIntersection(MinPathFinder leftpath, MinPathFinder toppath, TwoDLoc leftloc, TwoDLoc toploc) {

		// find the best combined source
		leftloc.set(patchSize-1,0);          // upper left corner
		toploc.set(patchSize-1,0);           //  of the image
		double bestcost = leftpath.costOf(patchSize-1,0)
		    + toppath.costOf(patchSize-1,0);
		
		for(int y = 0; y < overlapSize; y++) {
		    for(int x = 0; x < overlapSize; x++) {
			double cost = leftpath.costOf(patchSize-1-y,x)
			    +toppath.costOf(patchSize-1-x,y);
			if( bestcost > cost ) {
			    leftloc.set(patchSize-1-y,x);
			    toploc.set(patchSize-1-x,y);
			    bestcost = cost;
			}
		    }
		}
	    }
	}
