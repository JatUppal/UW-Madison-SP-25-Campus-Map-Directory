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

    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException, NullPointerException {
        // Stub method for midweek submission
        size++;
    }

    @Override
    public boolean containsKey(KeyType key) {
        return false; // stub
    }

    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        return null; // stub
    }

    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        return null; // stub
    }

    @Override
    public void clear() {
        size = 0; // stub
    }

    @Override
    public int getSize() {
        return size; // stub
    }

    @Override
    public int getCapacity() {
        return capacity; // stub
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
        assertFalse(map.containsKey("cat"));
    }

    /**
     * testPutAndGet(): get() stub should return null for any key
     */
    @Test
    public void testPutAndGet() {
        HashtableMap<String, Integer> map = new HashtableMap<>();
        map.put("a", 1);
        assertNull(map.get("a"));
    }
}

