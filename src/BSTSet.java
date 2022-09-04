

public class BSTSet<K extends Comparable<K>> implements Set<K> {
	public BSTSetNode<K> root;
	public int size;
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean full() {
		return false;
	}

	@Override
	public void clear() {
		root = null;
		this.size=0;
	}

	@Override
	public boolean find(K k) {
		BSTSetNode<K> temp = root;
		while (temp != null ) {
			if ( temp.key.compareTo(k) == 0)
				return true;
			if (temp.key.compareTo(k) > 0)
				temp = temp.left;
			else
				temp = temp.right;
		}
		return false;
	}

	@Override
	public int nbKeyComp(K k) {
		BSTSetNode<K> temp = root;
		int count = 1;
		while (temp != null && temp.key.compareTo(k) != 0) {
			if (temp.key.compareTo(k) > 0)
				temp = temp.left;
			else
				temp = temp.right;
			count++;
		}

		if (temp == null)
			count--;
		return count;		
	}

	@Override
	public boolean insert(K k) {
		if (root == null) {
			root = new BSTSetNode<K>(k);
			this.size++;

			return true;
		}
		BSTSetNode<K> p = root;
		BSTSetNode<K> q = null;
		while (p != null && p.key.compareTo(k) != 0) {
			q = p;

			if (p.key.compareTo(k) > 0)
				p = p.left;
			else
				p = p.right;
		}
		if (p != null && p.key.compareTo(k) == 0)
			return false;
		else {
			if (q.key.compareTo(k) > 0)
				q.left = new BSTSetNode<K>(k);
			else
				q.right =  new BSTSetNode<K>(k);
			this.size++;

			return true;
		}
	}

	@Override
	public boolean remove(K k) {
		return remove(k, root, null);

	}
	
	private boolean remove(K k, BSTSetNode<K> root, BSTSetNode<K> q) {
		BSTSetNode<K> p = root;
		// find the node and its parent to put them on p and q (q don't change if
		// root.key==k as the subTree root's parent)
		while (p != null && p.key.compareTo(k) != 0) {
			q = p;

			if (p.key.compareTo(k) > 0)
				p = p.left;
			else
				p = p.right;
		}

		if (p == null)
			return false;
		else {
			if (p.left == null && p.right == null) {
				// case 1 no childs
				if (this.root == p)
					this.root = null;
				else if (q.key.compareTo(k) > 0)
					q.left = null;
				else
					q.right = null;
				this.size--;

				return true;
			} else if ((p.left == null || p.right == null) && p.right != p.left) {
				// Case 2 One child

				if (this.root == p) {
					if (this.root.left == null)
						this.root = this.root.right;
					else
						this.root = this.root.left;
					this.size--;

					return true;
				}
				if (p.left == null)
					if (q.key.compareTo(k) > 0)
						q.left = p.right;
					else
						q.right = p.right;

				else if (q.key.compareTo(k) > 0)
					q.left = p.left;
				else
					q.right = p.left;
				this.size--;

				return true;
			} else {
				// Case 3 Two childs
				BSTSetNode<K> minFromRight = findMin(p.right);
			
				p.key = minFromRight.key;

				return remove(minFromRight.key, p.right, p);
			}

		}

	}
	
	private BSTSetNode<K> findMin(BSTSetNode<K> root) {
		if (root == null)
			return null;
		BSTSetNode<K> p = root;

		while (p.left != null)
			p = p.left;

		return p;
	}

	@Override
	public List<K> getKeys() {

		List<K> list = new LinkedList<K>();
		this.getKeys(this.root,list);
		return list;
	}
	private void getKeys(BSTSetNode<K> root, List<K> list) {
		if (root == null)
			return ;

		this.getKeys(root.left,list);
		list.insert(root.key);
		this.getKeys(root.right,list);
		
	}
	

}
