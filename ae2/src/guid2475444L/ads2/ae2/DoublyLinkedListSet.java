package guid2475444L.ads2.ae2;

/**
 * {@link DynamicSet} implementation using a {@link DoublyLinkedList} as the backing container
 */
public class DoublyLinkedListSet<E> implements DynamicSet<E> {

    @Override
    public void add(E x) {

    }

    @Override
    public void remove(E x) {

    }

    @Override
    public boolean contains(Object x) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public DynamicSet<E> union(DynamicSet<? extends E> other) {
        return null;
    }

    @Override
    public DynamicSet<E> intersect(DynamicSet<? extends E> other) {
        return null;
    }

    @Override
    public DynamicSet<E> minus(DynamicSet<? extends E> other) {
        return null;
    }

    @Override
    public boolean isSubsetOf(DynamicSet<?> other) {
        return false;
    }

}
