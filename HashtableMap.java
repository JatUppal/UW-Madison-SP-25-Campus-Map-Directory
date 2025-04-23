import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    protected class Pair {
        public KeyType key;
        public ValueType value;

        public Pair(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }

    protected LinkedList<Pair>[] table;
    protected int capacity;
    protected int size;

    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = (LinkedList<Pair>[]) new LinkedList[capacity];
    }

    public HashtableMap() {
        this(64);
    }
	
	/**
     * Adds a new key,value pair/mapping to this collection.It is ok that the value is null.
     * @param key the key of the key,value pair
     * @param value the value that key maps to
     * @throws IllegalArgumentException if key already maps to a value
     * @throws NullPointerException if key is null
     */

    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException, NullPointerException {
        if (key == null){
			throw new NullPointerException("Key cannot be null.");
		}
		int index = Math.abs(key.hashCode()) % capacity;
		// ensure bucket is intialized, else intialize it
		if (table[index] == null){
			table[index] = new LinkedList<>();
		}
		for (Pair pair : table[index]){
			if(pair.key.equals(key))
				throw new IllegalArgumentException("Duplicate key.");
		}
		table[index].add(new Pair(key, value));
		size++;
		
		 if ((double)size / capacity >= 0.8)
			resize();
    }
	
	// private helper method for dynamically growing hashtable
	private void resize(){
		capacity *= 2;
		LinkedList<Pair>[] oldTable = table;
		
		@SuppressWarnings("unchecked")
		LinkedList<Pair>[] newTable = (LinkedList<Pair>[]) new LinkedList[capacity];
		table = newTable;

		size = 0;
		for (LinkedList<Pair> bucket : oldTable) {
			if (bucket != null) {
				for (Pair pair : bucket) {
					put(pair.key, pair.value);
				}
			}
		}
	}

	/**
     * Checks whether a key maps to a value in this collection.
     * @param key the key to check
     * @return true if the key maps to a value, and false is the
     *         key doesn't map to a value
     */

    @Override
    public boolean containsKey(KeyType key) {
		int index = Math.abs(key.hashCode()) % capacity;
		if(table[index] == null)
			return false;
		for(Pair pair : table[index]){
			if(pair.key.equals(key))
				return true;
		}
		return false;	
    }
	
	/**
     * Retrieves the specific value that a key maps to.
     * @param key the key to look up
     * @return the value that key maps to
     * @throws NoSuchElementException when key is not stored in this
     *         collection
     */

    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
		int index = Math.abs(key.hashCode()) % capacity;
		for(Pair pair : table[index]){
			if(pair.key.equals(key))
				return pair.value;
		}
		throw new NoSuchElementException("Key not found: " + key);
    }

	/**
     * Remove the mapping for a key from this collection.
     * @param key the key whose mapping to remove
     * @return the value that the removed key mapped to
     * @throws NoSuchElementException when key is not stored in this
     *         collection
     */

    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        if(!containsKey(key))
			throw new NoSuchElementException("Key not found: " + key);
		int index = Math.abs(key.hashCode()) % capacity;
		for(Pair pair: table[index]){
			if(pair.key.equals(key)){
				ValueType value = pair.value;
				table[index].remove(pair);
				size--;
				return value;
			}
		}
		throw new NoSuchElementException("Key not found: " + key);	
    }

	/**
     * Removes all key,value pairs from this collection.
     */

    @Override
    public void clear() {
		for(int i = 0; i < table.length; i++){
			if(table[i] != null) {
				table[i].clear();
			}
		}
        size = 0; // stub
    }

	/**
     * Retrieves the number of keys stored in this collection.
     * @return the number of keys stored in this collection
     */

    @Override
    public int getSize() {
	    return size;
    }

	/**
     * Retrieves this collection's capacity.
     * @return the size of te underlying array for this collection
     */

    @Override
    public int getCapacity() {
        return capacity;
    }


    // Midweek Test Methods 	

    /**
     * testDefaultCapacity(): getCapacity() should return 64 for default constructor
     */
    @Test
    public void testDefaultCapacity() {
        HashtableMap<String, String> map = new HashtableMap<>();
        assertEquals(64, map.getCapacity());
    }

    /**
     * testGetSize: put() should increase size, and getSize() should reflect this
     */
    @Test
    public void testGetSize() {
        HashtableMap<String, String> map = new HashtableMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(2, map.getSize());
    }

    /**
     * testClear(): clear() should reset size to 0
     */
    @Test
    public void testClear() {
        HashtableMap<String, String> map = new HashtableMap<>();
        map.put("key", "value");
        map.clear();
        assertEquals(0, map.getSize());
    }

    /**
     * testContainsKey(): containsKey() stub returns false (default behavior)
     */
    @Test
    public void testContainsKey() {
        HashtableMap<String, String> map = new HashtableMap<>();
        map.put("cat", "meow");
        assertTrue(map.containsKey("cat"));
    }

    /**
     * testPutAndGet(): get() stub should return null for any key
     */
    @Test
    public void testPutAndGet() {
        HashtableMap<String, Integer> map = new HashtableMap<>();
        map.put("a", 1);
        assertEquals(1, map.get("a"));
    }
}

