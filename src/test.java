//import java.util.TreeSet;

//import org._3pq.jgrapht.util.FibonacciHeap;
//import org._3pq.jgrapht.util.FibonacciHeap.Node;

public class test {

	public static void main(String[] args) {
		RankPairingHeap rh = new RankPairingHeap(10);
		PairNode node1 = new PairNode(4,"a");
		PairNode node2 = new PairNode(3,"b");
		PairNode node3 = new PairNode(2,"c");
		PairNode node4 = new PairNode(1,"d");
		
		rh.insert(node1);
		rh.insert(node2);
		rh.insert(node3);
		rh.insert(node4);
		
		/* System.out.println(rh.findMin().getId());
		rh.deleteMin();
		System.out.println(rh.findMin().getId());
		rh.deleteMin();
		System.out.println(rh.findMin().getId());
		rh.deleteMin();
		System.out.println(rh.findMin().getId());
		*/
		rh.decreaseKey(2,node1);
		rh.deleteMin();
		System.out.println(rh.findMin().getId());
		rh.deleteMin();
		System.out.println(rh.findMin().getId());
		rh.deleteMin();
		System.out.println(rh.findMin().getId());
		
	}
}
