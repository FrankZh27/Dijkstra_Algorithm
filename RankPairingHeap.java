Class RankPairingHeap{
	
	pint rootSize;
	ArrayList<PairNode>[]  rootList = new ArrayList<PairNode>[size];

	public RankPairingHeap(int rootSize){
		this.rootSize = rootSize;
		for (int i = 0; i < rootSize; i++){
			rootList[i] = new ArrayList<PairNode>();
			//rootlist[i].add(null);
		}
		
	}

// Already done
	public void insert(PairNode node){
		if (rootList[node.getRank()] == null){
			rootList[node.getRank()].add(node);
		}
		else{
			merge(node, rootList[node.getRank()].get(0));
		}
	}

// To do
	public PairNode deleteMin(){
		PairNode node = findMin();
		rootList[node.getRank()].remove(0);
		
// Whenever we cut a subtree, new root add to the corresponding rootList according to its rank
		PairNode newRoot = node.left;
		PairNode cutRoot = null;
		
		rootList[newRoot.getRank()].add(newRoot);
		while (newRoot.right != null){
			cutRoot = newRoot.right;
			newRoot.right = null;
			rootList[cutRoot.getRank()].add(cutRoot);
			newRoot = cutRoot;
		}
	}

// To do
	public void decreaseKey(int newKey, PairNode node){

	}

	
// Already done
	public pairNode findMin(){
		PairNode minNode = null;
		int minValue = Integer.MAX_VALUE;
		for (int i = 0; i < rootList[i].length; i++){
			if(rootList[i].get(0) = null){
				continue;
			}
			else {
				if (rootList[i].get(0).getKey() < minValue){
					minNode = rootList[i].get(0);
				}
			}
		}
		return minNode;
	}

	// To do
	public PairNode merge(PairNode node1, PairNode node2){

	}


// preserve 3 rules in rank
	public rankPreserve(PairNode node){

	}



}