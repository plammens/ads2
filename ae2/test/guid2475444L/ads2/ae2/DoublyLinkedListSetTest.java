package guid2475444L.ads2.ae2;

import org.jetbrains.annotations.NotNull;


class DoublyLinkedListSetTest extends DynamicSetTest {

    @Override
    @NotNull
    protected DynamicSet<Integer> constructSet() {
        return new DoublyLinkedListSet<>();
    }

}