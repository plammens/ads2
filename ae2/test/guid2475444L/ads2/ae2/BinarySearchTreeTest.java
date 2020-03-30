package guid2475444L.ads2.ae2;

import org.jetbrains.annotations.NotNull;


class BinarySearchTreeTest extends OrderedDynamicSetTest {
    @Override
    protected @NotNull BinarySearchTree<Integer> constructTestSubject() {
        return new BinarySearchTree<>();
    }

    @Override
    protected @NotNull BinarySearchTree<Integer> getTestSubject() {
        return (BinarySearchTree<Integer>) testSubject;
    }
}