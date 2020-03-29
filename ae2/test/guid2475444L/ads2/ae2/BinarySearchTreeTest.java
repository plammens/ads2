package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


class BinarySearchTreeTest extends DynamicSetTest {
    // --------------- Config ---------------

    @Override
    protected @NotNull DynamicSet<Integer> constructTestSubject() {
        return new BinarySearchTree<>();
    }

    @Override
    protected @NotNull BinarySearchTree<Integer> getTestSubject() {
        return (BinarySearchTree<Integer>) testSubject;
    }


    // --------------- Tests ---------------

    @Override
    @Test
    void iterator() {
        assertArrayEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).toArray(),
                          testSubject.toArray());
    }

    @Override
    @Test
    void remove_Object_UpdatesCorrectly() {
        super.remove_Object_UpdatesCorrectly();
        assertArrayEquals(List.of(2, 3, 4, 5, 6, 8).toArray(), getTestSubject().toArray());
    }

    @Override
    void testToString() {
        super.testToString();
        assertEquals("{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}", testSubject.toString());
    }

    @Override
    void assertPropertiesHaveNotChanged() {
        super.assertPropertiesHaveNotChanged();
        assertEquals(INIT_SIZE, getTestSubject().size());
        assertFalse(getTestSubject().isEmpty());
    }

}