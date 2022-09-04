


public interface Array<T> {
	// Return the number of elements in the array.
	int size();
	
	// Return the capacity of the array. Upon instantiation, the capacity must be 0. After first insert, it becomes 1. After that, the capacity doubles whenever the array becomes full and a new insert is requested.
	int capacity();
	
	// Return element at position i. If i >= size, the method throws ArrayIndexOutOfBoundsException.
	T get(int i);
	
	// Update element at position i. If i >= size, the method throws ArrayIndexOutOfBoundsException.
	void set(int i, T e);
	
	// Add an element at the end of the array. This method increases size by 1. If size before add equals capacity, then capacity is doubled.
	void add(T e);
}
