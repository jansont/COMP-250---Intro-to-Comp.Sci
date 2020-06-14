
import java.util.ArrayList;
import java.util.Iterator;

public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k; 
	int			numLeaves;
	
	
	
	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 

		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else
			this.k = datalist.get(0).x.length;

		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}
		
	//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}
	
	//   KDTree methods
	
	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}
	

	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}
	
	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}
	
	class KDNode { 

		boolean leaf;
		Datum leafDatum;           //  only stores Datum if this is a leaf
		
		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		int splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  

		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		  //  You may think of them as "left" and "right" instead of "low" and "high", respectively
	
		
		/*
		 *  This method takes in an array of Datum and returns 
		 *  the calling KDNode object as the root of a sub-tree containing  
		 *  the above fields.
		 */
		
	
		KDNode(Datum[] datalist) throws Exception {
			
			if (datalist.length == 0) {
				throw new Exception("Can't create tree without data");
			}

			else if (datalist.length == 1) {
				leaf = true;
				numLeaves++;
				leafDatum = datalist[0];

				return;
			}
			
			else {
				int[] heighest = datalist[0].x.clone();
				int[] lowest = datalist[0].x.clone();
				int[] ranges = new int[k];
				int maxRange = 0;
				splitValue = 0;
			
				for (int k = 0; k < datalist.length; k++) { 	
					for(int i = 0; i < datalist[0].x.length; i++ ) {			
						
						if (datalist[k].x[i] > heighest[i]) {
							heighest[i] = datalist[k].x[i];
						}
						
						if (datalist[k].x[i] < lowest[i]) {
							lowest[i] = datalist[k].x[i];
						}
						
						ranges[i] = heighest[i] - lowest[i];
						if (ranges[i] > maxRange) {
							
							splitDim = i;
						}
					}
					
					splitValue = lowest[splitDim] + (ranges[splitDim] / 2);

				}
		
			ArrayList<Datum> highSide = new ArrayList<Datum>();
			ArrayList<Datum> lowSide = new ArrayList<Datum>();

			for (int i = 0; i < datalist.length; i++) {

				if (datalist[i].x[splitDim] <= splitValue) {
					lowSide.add(datalist[i]);
				} else {
					highSide.add(datalist[i]);
				}
			}
			
			
			Datum[] highArray = highSide.toArray(new Datum[highSide.size()]);
			Datum[] lowArray = lowSide.toArray(new Datum[lowSide.size()]);
			
			if (datalist.length > 1 && (highArray.length == 0 || lowArray.length == 0)) {
				numLeaves++;
				leafDatum = datalist[0];
				leaf = true;
			} else {
				leaf = false;
				lowChild = new KDNode(lowArray);
				highChild = new KDNode(highArray);
			
			}
		}
	}
		
		public Datum nearestPointInNode(Datum queryPoint) {
			Datum nearestPoint;
			Datum nearestPointNotQuerySide;
			double distance;
			double querySplitDistance;

			if (this.leaf){
				return this.leafDatum;
			}			
			if(queryPoint.x[splitDim] <= splitValue){
				nearestPoint = lowChild.nearestPointInNode(queryPoint);
				distance = distSquared(nearestPoint, queryPoint);
				querySplitDistance = (queryPoint.x[this.splitDim] - this.splitValue)*(queryPoint.x[this.splitDim] - this.splitValue);

				if(distance > querySplitDistance){
					nearestPointNotQuerySide = highChild.nearestPointInNode(queryPoint);
					double notQuerySideDistance = distSquared(nearestPointNotQuerySide, queryPoint);
					
					if(distance > notQuerySideDistance){
						nearestPoint = nearestPointNotQuerySide;
					}
				
				}
			}	else {
				nearestPoint = highChild.nearestPointInNode(queryPoint);
				distance = distSquared(nearestPoint, queryPoint);
				querySplitDistance = (queryPoint.x[this.splitDim] - this.splitValue)*(queryPoint.x[this.splitDim] - this.splitValue);

				if(distance > querySplitDistance){
					nearestPointNotQuerySide = lowChild.nearestPointInNode(queryPoint);
					double notQuerySideDistance = distSquared(nearestPointNotQuerySide, queryPoint);
					
					if(distance > notQuerySideDistance){
						nearestPoint = nearestPointNotQuerySide;
					}
				
			}
		}
			return nearestPoint;
		}
		
		// -----------------  KDNode helper methods (might be useful for debugging) -------------------

		public int height() {
			if (this.leaf) 	
				return 0;
			else {
				return 1 + Math.max( this.lowChild.height(), this.highChild.height());
			}
		}

		public int countNodes() {
			if (this.leaf)
				return 1;
			else
				return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
		}
		
		/*  
		 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
		 * of the subtree rooted at this KDNode.   The second element is the number of leaves
		 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
		 * to element 0 and size refers to element 1.
		 */
				
		public int[] sumDepths_numLeaves(){
			int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
			int[] return_sumDepths_numLeaves = new int[2];
			
			/*     
			 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
			 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
			 *  is one greater than the depth of each leaf in the subtree.
			 */
			
			if (this.leaf) {  // base case
				return_sumDepths_numLeaves[0] = 0;
				return_sumDepths_numLeaves[1] = 1;
			}
			else {
				sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
				sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
				return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
				return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			}	
			return return_sumDepths_numLeaves;
		}
		
	}

	public Iterator<Datum> iterator() {
		return new KDTreeIterator();
	}
	
	private class KDTreeIterator implements Iterator<Datum> {
		
		//   ADD YOUR CODE BELOW HERE
		int count = 0;
		ArrayList<KDNode> parents = new ArrayList<KDNode>();
		ArrayList<Datum> leaves = new ArrayList<Datum>();
		
		public KDTreeIterator() {
			
			KDNode current = rootNode;
			
			do{
				
				if (current != null) {
					parents.add(current);
					
					if (current.leaf)
						leaves.add(current.leafDatum);
						current = current.lowChild;
					
					} else {
						current = parents.remove(parents.size()-1).highChild;	
					}
			} while ((!parents.isEmpty())||current!=null);
			
			
		}

		public boolean hasNext() {
			return (count < leaves.size());	
		}
		
		public Datum next() {
		
			if (hasNext()) {
				return leaves.get(count++);
			} else
				return null;					
		}
	}
}



