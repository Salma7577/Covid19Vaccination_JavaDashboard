

public class BSTMap<K extends Comparable<K>, T> implements Map<K, T> {
	public BSTNode<K, T> root;
	private int size;

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
	public boolean update(K k, T e) {
		BSTNode<K, T> temp = this.findNode(k);
		if (temp == null)
			return false;
		temp.data = e;

		return true;
	}

	private BSTNode<K, T> findNode(K k) {
		BSTNode<K, T> temp = root;
		while (temp != null && temp.key.compareTo(k) != 0) {
			/////////////////////////////////////////////////////////////////////////////////////////////////////////
			if (temp.key.compareTo(k) > 0)
				temp = temp.left;
			else
				temp = temp.right;
		}
		return temp;
	}

	@Override
	public Pair<Boolean, T> retrieve(K k) {
		BSTNode<K, T> temp = this.findNode(k);

		if (temp != null)
			return new Pair<Boolean, T>(true, temp.data);
		else
			return new Pair<Boolean, T>(false, null);
	}

	@Override
	public int nbKeyComp(K k) {
		BSTNode<K, T> temp = root;
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
	public boolean insert(K k, T e) {

		if (root == null) {
			root = new BSTNode<K, T>(k, e);
			
			this.size++;

			return true;
		}
		BSTNode<K, T> p = root;
		BSTNode<K, T> q = null;
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
				q.left = new BSTNode<K, T>(k, e);
			else
				q.right = new BSTNode<K, T>(k, e);
			this.size++;

			return true;
		}

	}

	@Override
	public boolean remove(K k) {

		return remove(k, root, null);
	}

	private boolean remove(K k, BSTNode<K, T> root, BSTNode<K, T> q) {
		BSTNode<K, T> p = root;
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
				BSTNode<K, T> minFromRight = findMin(p.right);
				p.data = minFromRight.data;
				p.key = minFromRight.key;

				return remove(minFromRight.key, p.right, p);
			}

		}

	}

	private BSTNode<K, T> findMin(BSTNode<K, T> root) {
		if (root == null)
			return null;
		BSTNode<K, T> p = root;

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

	private void getKeys(BSTNode<K, T> root, List<K> list) {
		if (root == null)
			return ;

		this.getKeys(root.left,list);
		list.insert(root.key);
		this.getKeys(root.right,list);
		
	}


}
