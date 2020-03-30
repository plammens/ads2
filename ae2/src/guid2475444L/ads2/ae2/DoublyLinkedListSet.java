package guid2475444L.ads2.ae2;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.jetbrains.annotations.NotNull;


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
    @SuppressWarnings("unchecked")
    public boolean remove(Object x) {
        try {
            Iterator<E> iter = list.iterator();
            while (iter.hasNext()) {
                int comparison = iter.next().compareTo((E) x);
                if (comparison == 0) {
                    iter.remove();
                    return true;
                }
                else if (comparison > 0) return false;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
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

    // ----- methods inherited from AbstractDynamicSet<E> -----

    @Override
    protected DynamicSet<E> fromSorted(Iterator<E> iter) {
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
