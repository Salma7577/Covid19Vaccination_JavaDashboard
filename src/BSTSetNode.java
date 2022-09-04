

public class BSTSetNode<K extends Comparable<K>> {
	
	public K key;

	public BSTSetNode<K> left,right;
	
	public BSTSetNode(K key) {
		this.key = key;
	
	}
	
	public BSTSetNode(K key, BSTSetNode<K> left , BSTSetNode<K> right) {
		this.key = key;		
		this.left = left;
		this.right = right;
	}
}
