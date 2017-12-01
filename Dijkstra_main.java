import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org._3pq.jgrapht.util.*;
import org._3pq.jgrapht.util.FibonacciHeap.Node;




class pairComparator implements Comparator<pair> {
	public int compare(pair a, pair b) {
		return a.distance - b.distance;
	}
}
public class Dijkstra_main {

	public static void main(String[] args) throws FileNotFoundException {
		//PreProcess Data:
		//System.out.println(System.getProperty("user.dir"));
	    File file = new File("input_vertex.txt");
	    Scanner s = new Scanner(file);
    	String line = s.next();
    	String[] point = line.split(";");
    	s.close();
    	Vertex[] V = new Vertex[point.length];
    	// HashMap<String, Node> map = new HashMap<String, Node>();
    	for (int i = 0; i < point.length; i++) {
    		Vertex temp = new Vertex(point[i]);
    		V[i] = temp;
    	}
		//System.out.println(System.getProperty("user.dir"));
	    file = new File("input_edges.txt");
	    s = new Scanner(file);
	    while (s.hasNext()) {
	    	//System.out.println(s.next());
	    	String temp = s.next();
	    	String[] tempArray = temp.split(",");
	    	Edge e = new Edge(tempArray[1], Integer.valueOf(tempArray[2]));
	    	for (int i = 0; i < V.length; i++) {
	    		if (V[i].id.equals(tempArray[0])) {
	    			V[i].addEdge(e);
	    			break;
	    		}
	    	}
	    }
	    s.close();
	    
	    
	    
	    String start = "D";
	    String end = "E";
	    int i = findIndex(V, start);
	    int j = findIndex(V, end);
	    if (i == -1 || j == -1) {
	    	System.out.println("GoodBye");
	    	return;
	    }
	    long startTime = System.currentTimeMillis();
	    int result = dijkstra_pq(V, i, j);
	    long endTime   = System.currentTimeMillis();
	    long totalTime = endTime - startTime;
	    System.out.println("Running time is "+totalTime+"ms");
	    if (result == Integer.MAX_VALUE) {
	    	System.out.println("Cannot reach");
	    }
	    else System.out.println("Shortest path length: "+result);
	    return;
	}
	
	public static int dijkstra_pq(Vertex[] V, int i, int j) {
		PriorityQueue<pair> pq = new PriorityQueue<pair>(new pairComparator());
		//FibonacciHeap fh = new FibonacciHeap();
		//HashMap<String, Node> map= new HashMap<String, Node>();
		for (int k = 0; k < V.length; k++) {
			if (k == i) {
				pair temp = new pair(V[k], 0);
				pq.offer(temp);
			}
			else {
				pair temp = new pair(V[k], Integer.MAX_VALUE);
				pq.offer(temp);
			}
		}
		while (!pq.isEmpty()) {
			pair temp = pq.poll();
			if (temp.endPoint.id.equals(V[j].id)) {
				return temp.distance;
			}
			Vertex newId = temp.endPoint;
			for (int k = 0; k < newId.nb.size(); k++) {
				Edge newEdge = newId.nb.get(k);
				Iterator<pair> it = pq.iterator();
				pair newTemp = temp;
				int min = Integer.MAX_VALUE;
				while (it.hasNext()) {
					newTemp = it.next();
					if (newTemp.endPoint.id.equals(newEdge.endPoint)) {
						min = Math.min(newTemp.distance, newEdge.weight + temp.distance);
						pq.remove(newTemp);//??????????????????????????????
						break;
					}
				}
				pq.offer(new pair(newTemp.endPoint, min));
			}
		}
		return -1;
	}
	
	
	public static int dijkstra_fiheap(Vertex[] V, int i, int j) {
		FibonacciHeap fh = new FibonacciHeap();
		HashMap<String, Node> map= new HashMap<String, Node>();
		for (int k = 0; k < V.length; k++){
			if (k == i){
				Node temp = new Node(0);
				map.put(V[k].id, temp);
				fh.insert(temp, 0);
			}
			else{
				Node temp = new Node(Integer.MAX_VALUE);
				map.put(V[k].id, temp);
				fh.insert(temp, Integer.MAX_VALUE);
			}
		}
		
		while (!fh.isEmpty()){
			Node temp = fh.min();
			
		}
		
		
		return 0;
	}
	
	
	
	public static int findIndex(Vertex[] V, String id) {
		for (int i = 0; i < V.length; i++) {
			if (V[i].id.equals(id)) {
				return i;
			}
		}
		return -1;
	}
}
