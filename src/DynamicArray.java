

public class DynamicArray<T> implements Array<T> {

	private int size = 0;
	private T array[];

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int capacity() {
		if (this.array == null)
			return 0;

		return this.array.length;
	}

	@Override
	public T get(int i) throws ArrayIndexOutOfBoundsException {

		if (i >= this.size)
			throw new ArrayIndexOutOfBoundsException();
		return this.array[i];
	}

	@Override
	public void set(int i, T e) throws ArrayIndexOutOfBoundsException {
		if (i >= this.size)
			throw new ArrayIndexOutOfBoundsException();
		this.array[i] = e;

	}

	@Override
	public void add(T e) {

		if (this.size == this.capacity()) {

			if (this.capacity() == 0) {

				this.array = (T[]) new Object[1];

				this.array[0] = e;

				this.size++;
				return;

			}

			else {
				T[] temp = (T[]) new Object[this.capacity() * 2];
				for (int i = 0; i < this.size; i++) {
					temp[i] = this.array[i];
				}

				this.array = temp;
				this.array[this.size++] = e;
				return;
			}

		}

		this.array[this.size++] = e;
	}

}
