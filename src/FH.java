public class FH {
	newNode[] allRoot;

	public FH(int size) {
		allRoot = new newNode[size];
		for (int i = 0; i < size; i++) {
			allRoot[i] = null;
		}
	}

	public void insert(newNode a) {
		if (allRoot[a.rank] == null) {
			allRoot[a.rank] = a;
		} else {
			merge(a, allRoot[a.rank]);
		}
	}

	public newNode deletMin() {
		int i = findMin();
		newNode cur = allRoot[i];
		newNode child = cur.leftChild;
		allRoot[i] = null;
		while (child != null) {
			child.parent = null;
			child.left = null;
			newNode temp = child;
			child = child.right;
			temp.right = null;
			insert(temp);
		}
		cur.rank = 0;
		cur.mark = 0;
		cur.parent = null;
		cur.left = null;
		cur.leftChild = null;
		cur.right = null;
		// System.out.println(cur.id + " " + cur.key);
		return cur;
	}

	public int findMin() {
		//int count = 0;
		int result = -5;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < allRoot.length; i++) {
			if (allRoot[i] == null) {
				//count++;
				continue;
			} else {
				if (allRoot[i].key <= min) {
					min = allRoot[i].key;
					result = i;
				}
			}
		}
		//System.out.println(count);
		return result;
	}

	public void decreaseKey(newNode a, int target) {
		a.key = target;
//		System.out.print(a.id+"  "+a.rank+"  ");
		if (a.parent == null) {
//			System.out.println();
			return;
		}
		newNode father = a.parent;
//		System.out.print(father.id+"  "+father.rank);
//		System.out.println();
		a.parent = null;
		if (a.left != null && a.right != null) {
			a.left.right = a.right;
			a.right.left = a.left;
			a.left = null;
			a.right = null;
			father.mark += 1;
		} else if (a.left != null && a.right == null) {
			a.left.right = null;
			a.left = null;
			father.mark += 1;
		} else if (a.left == null && a.right != null) {
			a.right.left = null;
			father.leftChild = a.right;
			a.right = null;
			father.mark += 1;
		} else {
			father.leftChild = null;
			father.mark += 1;
		}
		if (a.mark >= 1) {
			a.rank -= a.mark;
			a.mark = 0;
			
		}
		insert(a);
		if (father.mark == 1) {
//			if (father.parent == null) {
//				allRoot[father.rank] = null;
//				father.rank -= 1;
//				father.mark -= 1;
//				insert(father);
//			}
		} else if (father.mark >= 2) {
			father.rank -= 2;
			father.mark = 0;
			decreaseKey(father, father.key);
		}
//		System.out.print("new: "+a.id+"  "+a.rank+"  "+father.id+"   "+father.rank);
//		System.out.println();
		return;
	}

	public void merge(newNode smaller, newNode larger) {
		if (smaller.key > larger.key) {
			newNode temp = smaller;
			smaller = larger;
			larger = temp;
		}
		int rrr = smaller.rank;
		if (smaller.leftChild == null) {
			smaller.leftChild = larger;
			larger.parent = smaller;
			smaller.rank += 1;
		} else {
			newNode temp = smaller.leftChild;
			temp.left = larger;
			larger.right = temp;
			larger.parent = smaller;
			smaller.leftChild = larger;
			smaller.rank += 1;
		}
		if (allRoot[smaller.rank] == null) {
			allRoot[smaller.rank] = smaller;
		} else {
			merge(smaller, allRoot[smaller.rank]);
		}
		allRoot[rrr] = null;
		return;
	}
}