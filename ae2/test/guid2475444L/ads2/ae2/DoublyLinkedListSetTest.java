package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


class DoublyLinkedListSetTest extends DynamicSetTest {

    // --------------- Config ---------------

    @Override
    protected @NotNull DoublyLinkedListSet<Integer> getTestSubject() {
        return (DoublyLinkedListSet<Integer>) testSubject;
    }

    @Override
    protected @NotNull DynamicSet<Integer> constructTestSubject() {
        return new DoublyLinkedListSet<>();
    }


    // --------------- Test ---------------

    @Override
    @Test
    void iterator() {
        assertArrayEquals(INIT_LIST.stream().distinct().toArray(), testSubject.toArray());
    }

}