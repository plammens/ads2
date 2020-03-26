package guid2475444L.ads2.ae2;

import java.util.Collection;
import java.util.Iterator;

import org.jetbrains.annotations.NotNull;


/**
 * {@link DynamicSet} implementation using a {@link DoublyLinkedList} as the backing container
 * @author - Paolo Lammens (2475444L)
 */
public class DoublyLinkedListSet<E> implements DynamicSet<E> {

    private DoublyLinkedList<E> list = new DoublyLinkedList<>();

    // ----- methods inherited from DynamicSet<E> -----

    @Override
    public boolean add(E x) {
        if (!list.contains(x)) return list.add(x);
        return false;
    }

    @Override
    public boolean remove(Object x) {
        return list.remove(x);
    }

    @Override
    public boolean contains(Object x) {
        return list.contains(x);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public DynamicSet<E> union(DynamicSet<? extends E> other) {
        DynamicSet<E> union = new DoublyLinkedListSet<>();
        union.addAll(this);
        union.addAll(other);
        return union;
    }

    @Override
    public DynamicSet<E> intersect(DynamicSet<? extends E> other) {
        DynamicSet<E> intersection = new DoublyLinkedListSet<>();
        for (E elem : this)
            if (other.contains(elem)) intersection.add(elem);
        return intersection;
    }

    @Override
    public DynamicSet<E> minus(DynamicSet<? extends E> other) {
        DynamicSet<E> difference = new DoublyLinkedListSet<>();
        difference.addAll(this);
        difference.removeAll(other);
        return difference;
    }

    @Override
    public boolean isSubsetOf(DynamicSet<?> other) {
        return other.containsAll(this);
    }

    // ----- methods inherited from Collection<E> ----

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean changed = false;
        for (E elem : c)
            if (add(elem)) changed = true;
        return changed;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return list.retainAll(c);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        // noinspection SuspiciousToArrayCall
        return list.toArray(a);
    }

}
