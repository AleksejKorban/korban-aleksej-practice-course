import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyOwnLinkedListTest {

    private MyOwnLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyOwnLinkedList<>();
    }

    @Test
    void testAddAndGetPhones() {
        list.addFirst("Google Pixel");
        list.addLast("Samsung Galaxy");
        list.add(1, "Apple iPhone");
        assertEquals(3, list.size());
        assertEquals("Google Pixel", list.getFirst());
        assertEquals("Samsung Galaxy", list.getLast());
        assertEquals("Apple iPhone", list.get(1));
        assertEquals("[Google Pixel--Apple iPhone--Samsung Galaxy] (size=3)", list.toString());
    }

    @Test
    void testRemovePhones() {
        list.addLast("Nokia 3310");
        list.addLast("Sony Xperia");
        list.addLast("Poco");
        assertEquals("Nokia 3310", list.removeFirst());
        assertEquals("Poco", list.removeLast());
        assertEquals("Sony Xperia", list.remove(0));
        assertEquals(0, list.size());
    }
    @Test
    void testExceptions() {
        assertThrows(IllegalStateException.class,()->list.getFirst());
        assertThrows(IllegalStateException.class,()->list.removeLast());
        list.addLast("home phone");
        assertThrows(IndexOutOfBoundsException.class,()->list.get(5));
        assertThrows(IndexOutOfBoundsException.class,()->list.add(2, "mobile phone"));
        assertThrows(IndexOutOfBoundsException.class,()->list.remove(3));
    }
}
