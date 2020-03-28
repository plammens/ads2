package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


class DoublyLinkedListTest extends CollectionTest {
    // --------------- Config ---------------

    @Override
    protected @NotNull DoublyLinkedList<Integer> constructSubject() {
        return new DoublyLinkedList<>();
    }

    @Override
    protected @NotNull DoublyLinkedList<Integer> getTestSubject() {
        return (DoublyLinkedList<Integer>) testSubject;
    }

    DoublyLinkedListTest() {
        super(Arrays.asList(1, 1, 2, 3, null, 5, 6, 7, 8, 9),  // init list
              List.of(-1, 12, 42, 4, new Object()),  // non-members sample
              10);  // init size
    }


    // --------------- Tests ---------------

    @Test
    void getHead() {
        assertEquals(1, getTestSubject().getFirst());
    }

    @Test
    void getTail() {
        assertEquals(9, getTestSubject().getLast());
    }

    @Override
    @Test
    void add() {
        assertTrue(getTestSubject().add(42));
        assertEquals(INIT_SIZE + 1, getTestSubject().size());
        assertEquals(42, getTestSubject().get(INIT_SIZE));
        assertEquals(42, getTestSubject().getLast());
    }

    @Test
    void add_index() {
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().add(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().add(INIT_SIZE, 0));
        assertPropertiesHaveNotChanged();

        getTestSubject().add(0, 42);
        assertEquals(42, getTestSubject().getFirst());
        assertEquals(INIT_SIZE + 1, getTestSubject().size());
    }

    @Override
    @Test
    void remove() {
        assertFalse(getTestSubject().remove(Integer.valueOf(10)));
        assertPropertiesHaveNotChanged();

        assertTrue(getTestSubject().remove(null));
        assertEquals(INIT_SIZE - 1, getTestSubject().size());
        assertFalse(getTestSubject().contains(null));
    }

    @Test
    void remove_index() {
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().remove(INIT_SIZE));
        assertPropertiesHaveNotChanged();

        assertEquals(5, getTestSubject().remove(5));
        assertEquals(INIT_SIZE - 1, getTestSubject().size());
        assertFalse(getTestSubject().contains(5));
        assertNull(getTestSubject().remove(4));
    }

    @Override
    @Test
    void containsAll() {
        assertTrue(getTestSubject().containsAll(INIT_LIST));
        assertTrue(getTestSubject().containsAll(List.of(3, 7)));
        assertFalse(getTestSubject().containsAll(List.of(1, 10)));
    }

    @Override
    @Test
    void addAll() {
        assertTrue(getTestSubject().addAll(INIT_LIST));
        assertEquals(2 * INIT_SIZE, getTestSubject().size());
        assertEquals(1, getTestSubject().get(INIT_SIZE));
    }

    @Test
    void addAll_index() {
        assertTrue(getTestSubject().addAll(6, INIT_LIST));
        assertEquals(1, getTestSubject().get(6));
        assertEquals(9, getTestSubject().get(6 + INIT_SIZE - 1));
        assertEquals(6, getTestSubject().get(6 + INIT_SIZE));
    }

    @Override
    @Test
    void removeAll() {
        assertFalse(getTestSubject().removeAll(List.of(42, 11, 32)));
        assertPropertiesHaveNotChanged();

        assertTrue(getTestSubject().removeAll(List.of(1, 7)));
        assertEquals(INIT_SIZE - 3, getTestSubject().size());
        assertTrue(getTestSubject().removeAll(INIT_LIST));
        assertEquals(0, getTestSubject().size());
        assertTrue(getTestSubject().isEmpty());
    }

    @Override
    @Test
    void retainAll() {
        assertFalse(getTestSubject().retainAll(INIT_LIST));
        assertPropertiesHaveNotChanged();

        assertTrue(getTestSubject().retainAll(List.of(1, 7)));
        assertEquals(3, getTestSubject().size());
        assertTrue(getTestSubject().containsAll(List.of(1, 7)));
    }

    @Override
    @Test
    void iterator() {
    }

    @Test
    void get() {
        assertEquals(1, getTestSubject().get(0));
        assertNull(getTestSubject().get(4));
        assertEquals(5, getTestSubject().get(5));
        assertEquals(9, getTestSubject().get(9));
    }

    @Test
    void set() {
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().set(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().set(INIT_SIZE, 0));
        assertEquals(1, getTestSubject().set(1, null));
        assertNull(getTestSubject().set(4, 0));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void indexOf() {
        assertEquals(-1, getTestSubject().indexOf(10));
        assertEquals(0, getTestSubject().indexOf(1));
        assertEquals(4, getTestSubject().indexOf(null));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void lastIndexOf() {
        assertEquals(-1, getTestSubject().lastIndexOf(10));
        assertEquals(1, getTestSubject().lastIndexOf(1));
        assertEquals(4, getTestSubject().lastIndexOf(null));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void subList() {
        assertThrows(IndexOutOfBoundsException.class, () -> getTestSubject().subList(-1, -1));
        assertEquals(getTestSubject(), getTestSubject().subList(0, INIT_SIZE));
        assertEquals(List.of(), getTestSubject().subList(5, 5));
        assertEquals(List.of(1, 2, 3), getTestSubject().subList(1, 4));
        assertPropertiesHaveNotChanged();

        List<Integer> subList = getTestSubject().subList(1, 6);
        subList.clear();
        assertEquals(List.of(1, 6, 7, 8, 9), getTestSubject());

        subList = getTestSubject().subList(0, 0);
        subList.add(42);
        assertEquals(42, getTestSubject().get(0));
    }

    @Override
    @Test
    @SuppressWarnings({"SimplifiableJUnitAssertion"})
    void testEquals() {
        assertTrue(getTestSubject().equals(getTestSubject()));
        assertTrue(getTestSubject().equals(INIT_LIST));
        assertFalse(getTestSubject().equals(List.of()));
        assertTrue(new DoublyLinkedList<Integer>().equals(List.of()));
        assertPropertiesHaveNotChanged();
    }

    @Override
    @Test
    void testToString() {
        assertEquals("< 1 ⇄ 1 ⇄ 2 ⇄ 3 ⇄ null ⇄ 5 ⇄ 6 ⇄ 7 ⇄ 8 ⇄ 9 >", getTestSubject().toString());
    }

    @Override
    @Test
    void toArray() {
        assertArrayEquals(INIT_LIST.toArray(), getTestSubject().toArray());
        Integer[] oversized = new Integer[INIT_SIZE + 3];
        assertArrayEquals(INIT_LIST.toArray(oversized), getTestSubject().toArray(oversized));
    }

    @Override
    void assertPropertiesHaveNotChanged() {
        super.assertPropertiesHaveNotChanged();
        assertEquals(1, getTestSubject().getFirst());
        assertEquals(9, getTestSubject().getLast());
    }

}