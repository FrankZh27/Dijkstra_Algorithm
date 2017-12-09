import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.Math;

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
	    
	    
	    
	    String start = "A";
	    String end = "G";
	    int i = findIndex(V, start);
	    int j = findIndex(V, end);
	    if (i == -1 || j == -1) {
	    	System.out.println("GoodBye");
	    	return;
	    }
	    long startTime = System.nanoTime();
	    int result = dijkstra_pq(V, i, j);
	    long endTime   = System.nanoTime();
	    long totalTime = endTime - startTime;
	    System.out.println("Running time is "+totalTime+"ns");
	    if (result == Integer.MAX_VALUE) {
	    	System.out.println("Cannot reach");
	    }
	    else System.out.println("Shortest path length: "+result);
	    
	    
	    System.out.println();
	    System.out.println();
	    System.out.println();
	    
	    startTime = System.nanoTime();
	    result = dijkstra_rankheap(V, i, j);
	    endTime   = System.nanoTime();
	    totalTime = endTime - startTime;
	    System.out.println("Running time is "+totalTime+"ns");
	    if (result == Integer.MAX_VALUE) {
	    	System.out.println("Cannot reach");
	    }
	    else System.out.println("Shortest path length: "+result);
	    
	    
	    
	    System.out.println();
	    System.out.println();
	    System.out.println();
	    
	    startTime = System.nanoTime();
	    result = dijkstra_fiheap(V, i, j);
	    endTime   = System.nanoTime();
	    totalTime = endTime - startTime;
	    System.out.println("Running time is "+totalTime+"ns");
	    if (result == Integer.MAX_VALUE) {
	    	System.out.println("Cannot reach");
	    }
	    else System.out.println("Shortest path length: "+result);
	    
	    return;
	}
	
	public static int dijkstra_pq(Vertex[] V, int i, int j) {
		PriorityQueue<pair> pq = new PriorityQueue<pair>(new pairComparator());
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
	
	
	public static int dijkstra_rankheap(Vertex[] V, int i, int j) {
		
		int size = (int) (Math.ceil(1.44*(Math.log(V.length)/(Math.log(2.0)))));  // convert to logn length
		
		RankPairingHeap rh = new RankPairingHeap(size);
		Map<String, PairNode> map = new HashMap<String, PairNode>();
		Map<PairNode, Vertex> verMap = new HashMap<>();
		for (int k = 0; k < V.length; k++) {
			if (k == i) {
				PairNode cur = new PairNode(0, V[k].id);
				map.put(V[k].id, cur);
				verMap.put(cur, V[k]);
				rh.insert(cur);
			}
			else {
				PairNode cur = new PairNode(Integer.MAX_VALUE, V[k].id);
				map.put(V[k].id, cur);
				verMap.put(cur, V[k]);
				rh.insert(cur);
			}
		}
		while (rh.rootList != null) {
			// newNode temp = f.deletMin();
			PairNode temp = rh.deleteMin();
			if (temp.getId().equals(V[j].id)) {
				return temp.getKey();
			}
			int base = temp.getKey();
			Vertex t = verMap.get(temp);
			List<Edge> neighbor = t.nb;
			for (Edge e : neighbor) {
				String endPoint = e.endPoint;
				int delta = e.weight;
				PairNode e_inFib = map.get(endPoint);
				if (e_inFib.getKey() <= base + delta) {
					continue;
				}
				else {
					rh.decreaseKey(base + delta, e_inFib);
				}
			}
		}
		return 0;
	}
	
	
	public static int dijkstra_fiheap(Vertex[] V, int i, int j) {
		int size = (int) (Math.ceil(1.44*(Math.log(V.length)/(Math.log(2.0))))); 
		FH f = new FH(size);
		
		Map<String, newNode> map = new HashMap<String, newNode>();
		Map<newNode, Vertex> verMap = new HashMap<>();
		for (int k = 0; k < V.length; k++) {
			if (k == i) {
				newNode cur = new newNode(0, V[k].id);
				map.put(V[k].id, cur);
				verMap.put(cur, V[k]);
				f.insert(cur);
			}
			else {
				newNode cur = new newNode(Integer.MAX_VALUE, V[k].id);
				map.put(V[k].id, cur);
				verMap.put(cur, V[k]);
				f.insert(cur);
			}
		}
		while (f.allRoot != null) {
			newNode temp = f.deletMin();
			if (temp.id.equals(V[j].id)) {
				return temp.key;
			}
			int base = temp.key;
			Vertex t = verMap.get(temp);
			List<Edge> neighbor = t.nb;
			for (Edge e : neighbor) {
				String endPoint = e.endPoint;
				int delta = e.weight;
				newNode e_inFib = map.get(endPoint);
				if (e_inFib.key <= base + delta) {
					continue;
				}
				else {
					f.decreaseKey(e_inFib, base + delta);
				}
			}
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
