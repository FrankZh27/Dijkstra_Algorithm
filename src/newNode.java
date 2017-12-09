
public class newNode {
	int key;
	int rank;
	int mark;
	String id;
	newNode parent;
	newNode leftChild;
	newNode left;
	newNode right;
	
	public newNode(int key, String id) {
		this.key = key;
		this.id = id;
		this.rank = 0;
		this.mark = 0;
		this.parent = null;
		this.leftChild = null;
		this.left = null;
		this.right = null;
	}
}
