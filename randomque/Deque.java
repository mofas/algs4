import java.util.Iterator;
import java.util.NoSuchElementException;
     
public class Deque<Item> implements Iterable<Item> {


	private class DequeIterator implements Iterator<Item> {		

		public boolean hasNext() {
			return false;
		}
		public Item next() {
			return null;
		}
		public void remove() {
      throw new UnsupportedOperationException();
  	}
	}

	private Item first = null;
	private Item last = null;
	private int size = 0;

	// construct an empty deque
  public Deque() {

  }
	// is the deque empty?
	public boolean isEmpty() {
		return true;
	}                 
	// return the number of items on the deque
	public int size() {
		return size;
	}                        
	// insert the item at the front
	public void addFirst(Item item) {

	}     
	// insert the item at the end
	public void addLast(Item item) {

	}
	// delete and return the item at the front
	public Item removeFirst() {
		return null;
	}                
	// delete and return the item at the end
	public Item removeLast() {
		return null;
	}                 
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new DequeIterator();
	} 

	// unit testing
	public static void main(String[] args) {

	}
}