public class RankPairingHeap{
	
	PairNode[]  rootList;

	public RankPairingHeap(int rootSize){
/*		for (int i = 0; i < rootSize; i++){
			rootList[i] = null;
		}
*/		rootList = new PairNode[rootSize];
	}

// Done
	public void insert(PairNode node){
		if (rootList[node.getRank()] == null){
			rootList[node.getRank()] = node;
		}
		else{
			merge(node, rootList[node.getRank()]);
		}
	}

// Done
	public PairNode deleteMin(){
		PairNode node = findMin();
		rootList[node.getRank()] = null;
		
		if (node.leftChild == null && node.rightChild == null){
			return node;
		}
		// !!!Here may cause ptr error
		PairNode newRoot = node.leftChild;
		PairNode rightRoot = newRoot.rightChild;

		while(rightRoot != null){
			rightRoot.parent = null;
			newRoot.parent = null;
			newRoot.rightChild = null;
		// Deal with left part, first preserve the rank
			if (newRoot.leftChild != null){
				newRoot.setRank(newRoot.leftChild.getRank() + 1);
				insert(newRoot);
			}
			else {
				newRoot.setRank(0);
				insert(newRoot);
			}

			newRoot = rightRoot;
			rightRoot = newRoot.rightChild;
		}
		// deal with the last right subtree's ptr
		if (newRoot.parent != null){              // no while loop runs, directly run this
			newRoot.parent.leftChild = null;
			newRoot.parent = null;
		}
		// see if the newRoot node is a single node
		if (newRoot.leftChild == null){
			newRoot.setRank(0);
			insert(newRoot);
		}
		else{
			newRoot.setRank(newRoot.leftChild.getRank() + 1);
			insert(newRoot);
		}
		
		
		return node;
	}

// To do
	public void decreaseKey(int newKey, PairNode node){
		node.setKey(newKey);
		if (node.parent == null){                                  // The node we want to decrease is in the rootList
			return;
		}
		// PairNode rightRoot = node.parent;
		PairNode parentR = node.parent;
// Be carefull of the ptrs!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (parentR.leftChild == node){
			if (node.leftChild == null && node.rightChild == null){
				node.parent = null;
				parentR.leftChild = null;
				preserveRank(parentR);
				node.setRank(0);
				insert(node);
				return;
			}

			if (node.leftChild == null){
				node.parent = null;
				parentR.leftChild = node.rightChild;
				node.rightChild = null;
				parentR.leftChild.parent = parentR;
				preserveRank(parentR);
				node.setRank(0);
				insert(node);
				return;

			}
			if (node.rightChild == null){
				node.parent = null;                             // Deal with ptr
				parentR.leftChild = null;
				preserveRank(parentR);
				node.setRank(node.leftChild.getRank() + 1);
				insert(node);
				return;
			}
			node.parent = null;
			parentR.leftChild = node.rightChild;
			node.rightChild = null;
			parentR.leftChild.parent = parentR;
			preserveRank(parentR);
			node.setRank(node.leftChild.getRank() + 1);
			insert(node);
			return;
		}
		else{
			if (node.leftChild == null && node.rightChild == null){
				node.parent = null;
				parentR.rightChild = null;
				preserveRank(parentR);
				node.setRank(0);
				insert(node);
				return;
			}

			if (node.leftChild == null){
				node.parent = null;
				parentR.rightChild = node.rightChild;
				node.rightChild = null;
				parentR.rightChild.parent = parentR;
				preserveRank(parentR);
				node.setRank(0);
				insert(node);
				return;

			}
			if (node.rightChild == null){
				node.parent = null;                             // Deal with ptr
				parentR.rightChild = null;
				preserveRank(parentR);
				node.setRank(node.leftChild.getRank() + 1);
				insert(node);
				return;
			}
			node.parent = null;
			parentR.rightChild = node.rightChild;
			node.rightChild = null;
			parentR.rightChild.parent = parentR;
			preserveRank(parentR);
			node.setRank(node.leftChild.getRank() + 1);
			insert(node);
			return;
		}
		
	}

	
// Done
	public PairNode findMin(){
		PairNode minNode = null;
		int minValue = Integer.MAX_VALUE;
		for (int i = 0; i < rootList.length; i++){
			if(rootList[i] == null){
				continue;
			}
			else {
				if (rootList[i].getKey() <= minValue){
					minNode = rootList[i];
					minValue = rootList[i].getKey();
				}
			}
		}
		return minNode;
	}

	// Done
	public void merge(PairNode node1, PairNode node2){
// keep node1 has the smallest key
		if (node2.getKey() < node1.getKey()){
			PairNode temp = node2;
			node2 = node1;
			node1 = temp;
		}
//merge until there is only one tree of a specific rank in the rootList
			PairNode tempChild1 = node1.leftChild;
			node1.leftChild = node2;
			node2.parent = node1;
			
			if (tempChild1 != null){
				tempChild1.parent = node2;
				node2.rightChild = tempChild1;	
			}
			rootList[node1.getRank()] = null;
			node1.setRank(node2.getRank() + 1);
			
			if (rootList[node1.getRank()] == null){
				rootList[node1.getRank()] = node1;
				return;
			}
			else {
				merge(node1, rootList[node1.getRank()]);
			}
		

	}

	public void preserveRank(PairNode node){
		if (node.parent == null){                          // If it is a root node
			rootList[node.getRank()] = null;
			if (node.leftChild == null){
				node.setRank(0);
			}
			else{
				node.setRank(node.leftChild.getRank() + 1);
			}
			insert(node);
			return;
		}
		if (node.leftChild == null && node.rightChild == null){
			node.setRank(0);
			preserveRank(node.parent);
			return;
		}

		if (node.leftChild == null){
			node.setRank(node.rightChild.getRank());
			preserveRank(node.parent);
			return;
		}
		if (node.rightChild == null){
			node.setRank(node.leftChild.getRank());
			preserveRank(node.parent);
			return;
		}
		if (node.leftChild.getRank() == node.rightChild.getRank()){
			node.setRank(node.leftChild.getRank() + 1);
			preserveRank(node.parent);
			return;
		}
		else {
			int r = (node.leftChild.getRank() > node.rightChild.getRank()) ? node.leftChild.getRank() : node.rightChild.getRank();
			node.setRank(r);
			preserveRank(node.parent);
			return;
		}
	}


}