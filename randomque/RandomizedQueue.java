import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	// construct an empty randomized queue

	private class RandomizedQueueIterator implements Iterator<Item> {		
		
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

	public RandomizedQueue(){

	}                 
	// is the queue empty?
	public boolean isEmpty(){
		return false;
	}                 
	// return the number of items on the queue
	public int size(){
		return 0;
	}                        
	// add the item
	public void enqueue(Item item){

	}           
	// delete and return a random item
	public Item dequeue(){
		return null;
	}                    
	// return (but do not delete) a random item
	public Item sample(){
		return null;
	}                     
	// return an independent iterator over items in random order
	public Iterator<Item> iterator(){
		return new RandomizedQueueIterator();
	}         
	// unit testing
	public static void main(String[] args){

	}   
}