package guid2475444L.ads2.ae2;

import org.jetbrains.annotations.NotNull;


class DoublyLinkedListSetTest extends OrderedDynamicSetTest {
    @Override
    protected @NotNull DoublyLinkedListSet<Integer> getTestSubject() {
        return (DoublyLinkedListSet<Integer>) testSubject;
    }

    @Override
    protected @NotNull OrderedDynamicSet<Integer> constructTestSubject() {
        return new DoublyLinkedListSet<>();
    }
}