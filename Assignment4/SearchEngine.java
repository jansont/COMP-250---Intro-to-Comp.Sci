import java.util.HashMap;

import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;
	private boolean first = true;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
		public void crawlAndIndex(String url) throws Exception {
			
			if (first) {
				initializeVisited();
				first = false;
			}
			
			if(this.internet.getVisited(url)) {
				throw new Exception("URL already visited");
			}
			try {
				this.internet.addVertex(url);
				this.internet.setVisited(url,true); 
				for(String a : noDupes(parser.getContent(url))){

					if(wordIndex.get(a) == null){
						ArrayList<String> url1 = new ArrayList<String>();
						url1.add(url);
						this.wordIndex.put(a, url1);
					} 
					else {
						ArrayList<String> garbage = wordIndex.get(a);
						garbage.add(url);
					}
				}
				for(String a : parser.getLinks(url)) {
					if(!internet.getVisited(a)) {
						crawlAndIndex(a);
					}
				}
				for(String a : parser.getLinks(url)) {
					internet.addEdge(url, a);
				}
	
			} catch(NullPointerException e) {
				System.out.println("URL is not on the Internet");
				System.exit(0);
			}
		}

		public void initializeVisited() {
			for(int i = 0; i< internet.getVertices().size() ; i++ ) {
				this.internet.setVisited(internet.getVertices().get(i), false);
				}
			}

		private ArrayList<String> noDupes(ArrayList<String> list){ 
			ArrayList<String> sorted = new ArrayList<String>(); 
			for (String element : list) { 
				if (!(sorted.contains(element))) { 
			    	  sorted.add(element); 
			       	} 
			    } 
				return sorted; 
		 } 	
		
		
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
	    ArrayList<Double> ranks=new ArrayList<Double>();
        for(int i=0; i< internet.getVertices().size();i++){
            ranks.add(i,1.0);
        }
        double convergence = -1;
        
        for(int k=0; k<internet.getVertices().size(); k++){

        do {
            for(int i=0; i<ranks.size();i++){
                 convergence = Math.abs(ranks.get(i) - computeRanks(internet.getVertices()).get(i));
                }
            for(int i=0;i< internet.getVertices().size();i++){
                ranks.set(i,computeRanks(internet.getVertices()).get(i));
                internet.setPageRank(internet.getVertices().get(i),ranks.get(i));

            }
		} while (convergence > epsilon || convergence == -1);
        
        for(int i=0; i<internet.getVertices().size();i++){
        	}
        } 
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
            ArrayList<Double> ranks = new ArrayList<Double>();
                for(int i=0; i<vertices.size();i++){
                    double multiplier = 0;
                    for (String outDegree : internet.getEdgesInto(vertices.get(i))) {
                        multiplier += (internet.getPageRank(outDegree) / internet.getOutDegree(outDegree));
                    }
                    ranks.add(0.5+0.5*multiplier);
                }
		return ranks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		HashMap<String, Double> results = new HashMap<String, Double>();
		ArrayList<String> empty = new ArrayList<String>();
		if(!wordIndex.containsKey(query.toLowerCase())) {
			return empty;
		}
		else {
			for(String a : wordIndex.get(query.toLowerCase())) {
				results.put(a, this.internet.getPageRank(a));
			}
			return Sorting.fastSort(results);
		}
	}
}