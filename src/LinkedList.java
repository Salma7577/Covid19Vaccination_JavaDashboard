

public class LinkedList<T> implements List<T> {

	private Node<T> head;

	private Node<T> current;

	private int size;

	@Override
	public boolean empty() {
		return head == null;
	}

	@Override
	public boolean full() {
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void findFirst() {
		this.current = this.head;

	}

	@Override
	public void findNext() {
		if(!this.last())
		this.current = this.current.next;

	}

	@Override
	public boolean last() {

		return this.current.next == null;
	}

	@Override
	public T retrieve() {

		return this.current.data;
	}

	@Override
	public void update(T e) {
		this.current.data = e;
	}

	@Override
	public void insert(T e) {
		Node<T> newNode = new Node<T>(e);

		if (head == null) {
			this.head = this.current = newNode;
			size++;
		} else {
			newNode.next = this.current.next;
			this.current.next = newNode;
			this.current=this.current.next;
			size++;
		}
	}

	@Override
	public void remove() {
		if (head == null)
			return;
		if (head == this.current) {
			head = head.next;
			this.current = head;
			size--;
			return;
		}

		Node<T> temp = head;

		while (temp != null) {
			if (temp.next == this.current)
				break;
			temp=temp.next;
			
		}
		temp.next = this.current.next;
		this.current = this.current.next;
		size--;
	}

	
	
	
	
}
