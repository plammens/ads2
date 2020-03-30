package guid2475444L.ads2.ae2;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * {@link DynamicSet} implementation using a {@link DoublyLinkedList} as the backing container
 * @author - Paolo Lammens (2475444L)
 */
public class DoublyLinkedListSet<E extends Comparable<E>> extends AbstractOrderedDynamicSet<E> {

    private DoublyLinkedList<E> list = new DoublyLinkedList<>();

    // ----- methods inherited from DynamicSet<E> -----

    @Override
    public boolean add(E x) {
        // insert maintaining sorted order:
        ListIterator<E> iter = list.listIterator();
        while (iter.hasNext()) {
            int comparison = iter.next().compareTo(x);
            if (comparison == 0) return false;
            else if (comparison > 0) {
                iter.previous();
                iter.add(x);
                return true;
            }
        }
        iter.add(x);
        return true;
    }

    @Override
    public boolean remove(Object x) {
        ListIterator<E> iter = find(x);
        if (iter == null) return false;
        iter.remove();
        return true;
    }

    @Override
    public boolean contains(Object x) {
        return find(x) != null;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // ----- methods inherited from AbstractDynamicSet<E> -----

    @Override
    protected OrderedDynamicSet<E> newEmptySet() {
        return new DoublyLinkedListSet<>();
    }

    @Override
    protected OrderedDynamicSet<E> fromSorted(Iterator<E> iter) {
        DoublyLinkedListSet<E> set = new DoublyLinkedListSet<>();
        iter.forEachRemaining(set.list::add);
        return set;
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
        // the naive approach gives O(n*m) complexity
        // sorting c gives us O(m*log(m) + n + m) = O(m*log(m)) complexity
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

    // ----- helper methods ----

    /**
     * Find an element in this set
     * @param o value to search for in this set
     * @return a list iterator whose last call to {@link ListIterator#next()} returned the element
     *         of the {@link #list} equal to {@code x}, or {@code null} if not present
     */
    @Nullable
    @SuppressWarnings("unchecked")
    private ListIterator<E> find(Object o) {
        try {
            E x = (E) o;
            if (list.getFirst().compareTo(x) > 0 || list.getLast().compareTo(x) < 0)
                return null;  // early exit if x is outwith the min-max range of this
            ListIterator<E> iter = list.listIterator();
            while (iter.hasNext()) {
                int comparison = iter.next().compareTo(x);
                if (comparison == 0) return iter;
                else if (comparison > 0) return null;
            }
            return null;
        } catch (ClassCastException e) {
            return null;  // incompatible type; cannot be present
        }
    }

}
