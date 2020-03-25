package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DoublyLinkedListTest {

    final static List<Integer> INIT_LIST = Arrays.asList(1, 1, 2, 3, null, 5, 6, 7, 8, 9);
    final static int INIT_SIZE = INIT_LIST.size();
    DoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
        list.addAll(INIT_LIST);
    }

    @Test
    void size() {
        assertEquals(INIT_SIZE, list.size());
    }

    @Test
    void isEmpty() {
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void getHead() {
        assertEquals(1, list.getFirst());
    }

    @Test
    void getTail() {
        assertEquals(9, list.getLast());
    }

    @Test
    void contains() {
        assertTrue(list.contains(3));
        assertTrue(list.contains(null));
        assertTrue(list.contains(1));
        assertTrue(list.contains(9));
        assertFalse(list.contains(10));
        assertFalse(list.contains(new Object()));
    }

    @Test
    void add() {
        assertTrue(list.add(42));
        assertEquals(INIT_SIZE + 1, list.size());
        assertEquals(42, list.get(INIT_SIZE));
        assertEquals(42, list.getLast());
    }

    @Test
    void add_index() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(INIT_SIZE, 0));
        assertPropertiesHaveNotChanged();

        list.add(0, 42);
        assertEquals(42, list.getFirst());
        assertEquals(INIT_SIZE + 1, list.size());
    }

    @Test
    void remove_object() {
        assertFalse(list.remove(Integer.valueOf(10)));
        assertPropertiesHaveNotChanged();

        assertTrue(list.remove(null));
        assertEquals(INIT_SIZE - 1, list.size());
        assertFalse(list.contains(null));
    }

    @Test
    void remove_index() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(INIT_SIZE));
        assertPropertiesHaveNotChanged();

        assertEquals(5, list.remove(5));
        assertEquals(INIT_SIZE - 1, list.size());
        assertFalse(list.contains(5));
        assertNull(list.remove(4));
    }

    @Test
    void containsAll() {
        assertTrue(list.containsAll(INIT_LIST));
        assertTrue(list.containsAll(List.of(3, 7)));
        assertFalse(list.containsAll(List.of(1, 10)));
    }

    @Test
    void addAll() {
        assertTrue(list.addAll(INIT_LIST));
        assertEquals(2 * INIT_SIZE, list.size());
        assertEquals(1, list.get(INIT_SIZE));
    }

    @Test
    void addAll_index() {
        assertTrue(list.addAll(6, INIT_LIST));
        assertEquals(1, list.get(6));
        assertEquals(9, list.get(6 + INIT_SIZE - 1));
        assertEquals(6, list.get(6 + INIT_SIZE));
    }

    @Test
    void removeAll() {
        assertFalse(list.removeAll(List.of(42, 11, 32)));
        assertPropertiesHaveNotChanged();

        assertTrue(list.removeAll(List.of(1, 7)));
        assertEquals(INIT_SIZE - 2, list.size());
        assertTrue(list.removeAll(INIT_LIST));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void retainAll() {
        assertFalse(list.retainAll(INIT_LIST));
        assertPropertiesHaveNotChanged();

        assertTrue(list.retainAll(List.of(1, 7)));
        assertEquals(3, list.size());
        assertTrue(list.containsAll(List.of(1, 7)));
    }

    @Test
    void clear() {
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void get() {
        assertEquals(1, list.get(0));
        assertNull(list.get(4));
        assertEquals(5, list.get(5));
        assertEquals(9, list.get(9));
    }

    @Test
    void set() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(INIT_SIZE, 0));
        assertEquals(1, list.set(1, null));
        assertNull(list.set(4, 0));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void indexOf() {
        assertEquals(-1, list.indexOf(10));
        assertEquals(0, list.indexOf(1));
        assertEquals(4, list.indexOf(null));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void lastIndexOf() {
        assertEquals(-1, list.lastIndexOf(10));
        assertEquals(1, list.lastIndexOf(1));
        assertEquals(4, list.lastIndexOf(null));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void testEquals() {
        assertTrue(list.equals(list));
        assertTrue(list.equals(INIT_LIST));
        assertFalse(list.equals(List.of()));
        assertTrue(new DoublyLinkedList<Integer>().equals(List.of()));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void subList() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, -1));
        assertEquals(list, list.subList(0, INIT_SIZE));
        assertEquals(List.of(), list.subList(5, 5));
        assertEquals(List.of(1, 2, 3), list.subList(1, 4));
        assertPropertiesHaveNotChanged();

        List<Integer> subList = list.subList(1, 6);
        subList.clear();
        assertEquals(List.of(1, 6, 7, 8, 9), list);

        subList = list.subList(0, 0);
        subList.add(42);
        assertEquals(42, list.get(0));
    }

    @Test
    void testToString() {
        assertEquals("< 1 ⇄ 1 ⇄ 2 ⇄ 3 ⇄ null ⇄ 5 ⇄ 6 ⇄ 7 ⇄ 8 ⇄ 9 >", list.toString());
    }

    @Test
    void toArray() {
        assertArrayEquals(INIT_LIST.toArray(), list.toArray());
        Integer[] oversized = new Integer[INIT_SIZE + 3];
        assertArrayEquals(INIT_LIST.toArray(oversized), list.toArray(oversized));
    }

    void assertPropertiesHaveNotChanged() {
        assertEquals(INIT_SIZE, list.size());
        assertFalse(list.isEmpty());
        assertEquals(1, list.getFirst());
        assertEquals(9, list.getLast());
    }

}