import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	
    	ArrayList<K> sortedUrls = new ArrayList<K>();
		sortedUrls.addAll(results.keySet());
    	return separate(results, sortedUrls);
    }

    private static <K,V extends Comparable> ArrayList<K> separate (HashMap<K,V> results, ArrayList<K> list) {
    	
    	ArrayList<K> left = new ArrayList<K>();
        ArrayList<K> right = new ArrayList<K>();
        int center;
 
        if (list.size() == 1) {    
            return list;
        } else {
            center = list.size()/2;
            // copy the left half of whole into the left.
            for (int i=0; i<center; i++) {
                    left.add(list.get(i));
            }
 
            //copy the right half of whole into the new arraylist.
            for (int i=center; i<list.size(); i++) {
                    right.add(list.get(i));
            }
 
            // Sort the left and right halves of the arraylist.
            left  = separate(results, left);
            right = separate(results, right);
 
            // Merge the results back together.
            merge(results, left, right, list);
        }
        return list;
	}

    private static <K, V extends Comparable> void merge (HashMap<K,V> results, ArrayList<K> left, ArrayList<K> right, ArrayList<K> list) {
    	
    	int l = 0;
        int r = 0;
        int z = 0;
 
        // As long as neither the left nor the right ArrayList has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
        while (l < left.size() && r < right.size()) {
            if((results.get(left.get(l)).compareTo(results.get(right.get(r))) > 0)){
                list.set(z, left.get(l));
                l++;
            } else {
                list.set(z, right.get(r));
                r++;
            }
            z++;
        }
 
        ArrayList<K> rest;
        int y;
        if (l >= left.size()) {
            // The left ArrayList has been use up...
            rest = right;
            y = r;
        } else {
            // The right ArrayList has been used up...
            rest = left;
            y = l;
        }
 
        // Copy the rest of whichever ArrayList (left or right) was not used up.
        for (int i=y; i<rest.size(); i++) {
            list.set(z, rest.get(i));
            z++;
        }
    }
}