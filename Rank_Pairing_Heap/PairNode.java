Class PairNode{
	private String id;
	private int key;
	private int rank;
	PairNode parent;
	PairNode leftChild;
	PairNode rightChild;

	public PairNode(int key, String id){
		this.key = key;
		this.id = id;
		this.rank = 0
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;

	}

	public int getId(){
		return id;
	}

	public String getKey(){
		return key;
	}

	public int getRank(){
		return rank;
	}

	public void setRank(int r){
		this.rank = r;
	}

	public void setKey(int k){
		this.key = k;
	}
}