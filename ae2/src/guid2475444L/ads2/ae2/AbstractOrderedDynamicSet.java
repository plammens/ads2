package guid2475444L.ads2.ae2;

import static java.lang.String.join;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.jetbrains.annotations.NotNull;


/**
 * Provides a default implementations for {@link OrderedDynamicSet} subclasses.
 * @param <E> type of elements
 */
public abstract class AbstractOrderedDynamicSet<E extends Comparable<E>> implements OrderedDynamicSet<E> {

    /**
     * Utility class to define an iterator that merges two sources of distinct, sorted elements
     * @param <T> type of elements
     * @see #unionIterator(Iterator, Iterator)
     * @see #intersectionIterator(Iterator, Iterator)
     */
    private static abstract class AbstractMergeIterator<T> implements Iterator<T> {
        Iterator<? extends T> it1, it2;
        protected T elem1, elem2;
        protected boolean advance1 = true, advance2 = true;
        private T cachedNext;

        public AbstractMergeIterator(Iterator<? extends T> it1, Iterator<? extends T> it2) {
            this.it1 = it1;
            this.it2 = it2;
        }

        @Override
        public boolean hasNext() {
            if (cachedNext != null) return true;
            return (cachedNext = pollNext()) != null;
        }

        @Override
        public T next() {
            T next;
            if ((next = cachedNext) != null) cachedNext = null;
            else if ((next = pollNext()) == null)
                throw new NoSuchElementException("end of iteration");
            return next;
        }

        /** @return next element in iteration if available, or else {@code null} */
        protected abstract T pollNext();

        protected void advance() {
            if (advance1) elem1 = it1.next();
            if (advance2) elem2 = it2.next();
        }
    }


    /**
     * Return an iterator that iterates in order over all distinct elements from two sorted &
     * distinct element sources.
     * @param it1 first source of sorted & distinct elements
     * @param it2 second source of sorted & distinct elements
     * @param <T> type of elements
     * @return a union iterator of {@code it1} and {@code it2} as described above
     */
    protected static <T extends Comparable<T>>
    Iterator<T> unionIterator(Iterator<? extends T> it1, Iterator<? extends T> it2) {
        return new AbstractMergeIterator<>(it1, it2) {
            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext();
            }

            @Override
            public T pollNext() {
                boolean it1HasNext = it1.hasNext(), it2HasNext = it2.hasNext();
                if (it1HasNext && it2HasNext) {
                    advance();
                    int comparison = elem1.compareTo(elem2);
                    if (comparison <= 0) {
                        advance1 = true;
                        advance2 = comparison == 0;
                        return elem1;
                    }
                    else {
                        advance1 = false;
                        advance2 = true;
                        return elem2;
                    }
                }
                else if (it1HasNext) return it1.next();
                else if (it2HasNext) return it2.next();
                else return null;
            }
        };
    }

    /**
     * Return an iterator that iterates in order over all elements present in both of the two given
     * sorted & distinct element sources.
     * @param it1 first source of sorted & distinct elements
     * @param it2 second source of sorted & distinct elements
     * @param <T> type of elements
     * @return an intersection iterator of {@code it1} and {@code it2} as described above
     */
    protected static <T extends Comparable<T>>
    Iterator<T> intersectionIterator(Iterator<? extends T> it1, Iterator<? extends T> it2) {
        return new AbstractMergeIterator<>(it1, it2) {
            @Override
            public T pollNext() {
                while (it1.hasNext() && it2.hasNext()) {
                    advance();
                    int comparison = elem1.compareTo(elem2);
                    if (comparison == 0) {
                        advance1 = advance2 = true;
                        return elem1;
                    }
                    else advance2 = !(advance1 = comparison < 0);
                }
                return null;
            }
        };
    }

    /**
     * Return an iterator that iterates over all elements in the first source that are not present
     * in the second source.
     * @param it1 first source of sorted & distinct elements
     * @param it2 second source of sorted & distinct elements
     * @param <T> type of elements
     * @return a difference iterator of {@code it1} and {@code it2} as described above
     */
    protected static <T extends Comparable<T>>
    Iterator<T> differenceIterator(Iterator<? extends T> it1, Iterator<? extends T> it2) {
        return new AbstractMergeIterator<>(it1, it2) {
            @Override
            public T pollNext() {
                while (it1.hasNext() && it2.hasNext()) {
                    advance();
                    int comparison = elem1.compareTo(elem2);
                    if (comparison < 0) {
                        advance1 = true;
                        advance2 = false;
                        return elem1;
                    }
                    else if (comparison == 0) advance1 = advance2 = true;
                    else {
                        advance1 = false;
                        advance2 = true;
                    }
                }
                if (it1.hasNext()) return it1.next();
                return null;
            }
        };
    }


    @Override
    public DynamicSet<E> union(@NotNull DynamicSet<? extends E> other) {
        if (other instanceof OrderedDynamicSet) //noinspection unchecked
            return union((OrderedDynamicSet<? extends E>) other);
        OrderedDynamicSet<E> union = newEmptySet();
        union.addAll(this);
        union.addAll(other);
        return union;
    }

    @Override
    public DynamicSet<E> intersect(@NotNull DynamicSet<? extends E> other) {
        if (other instanceof OrderedDynamicSet) //noinspection unchecked
            return intersect((OrderedDynamicSet<? extends E>) other);
        OrderedDynamicSet<E> intersection = newEmptySet();
        intersection.addAll(this);
        intersection.retainAll(other);
        return intersection;
    }

    @Override
    public DynamicSet<E> minus(@NotNull DynamicSet<? extends E> other) {
        if (other instanceof OrderedDynamicSet) //noinspection unchecked
            return minus((OrderedDynamicSet<? extends E>) other);
        OrderedDynamicSet<E> difference = newEmptySet();
        difference.addAll(this);
        difference.removeAll(other);
        return difference;
    }

    @Override
    public boolean isSubsetOf(@NotNull DynamicSet<?> other) {
        if (other instanceof OrderedDynamicSet)
            try {
                //noinspection unchecked
                return isSubsetOf((OrderedDynamicSet<? extends E>) other);
            } catch (ClassCastException e) { return false; }
        return other.containsAll(this);
    }

    // Optimized versions of set operations:

    /** optimized version of {@link #union(DynamicSet)} */
    public OrderedDynamicSet<E> union(@NotNull OrderedDynamicSet<? extends E> other) {
        return fromSorted(unionIterator(this.iterator(), other.iterator()));
    }

    /** optimized version of {@link #intersect(DynamicSet)} */
    public OrderedDynamicSet<E> intersect(@NotNull OrderedDynamicSet<? extends E> other) {
        return fromSorted(intersectionIterator(this.iterator(), other.iterator()));
    }

    /** optimized version of {@link #minus(DynamicSet)} */
    public OrderedDynamicSet<E> minus(@NotNull OrderedDynamicSet<? extends E> other) {
        return fromSorted(differenceIterator(this.iterator(), other.iterator()));
    }

    /** optimized version of {@link #isSubsetOf(DynamicSet)} */
    boolean isSubsetOf(@NotNull OrderedDynamicSet<? extends E> other) {
        return !differenceIterator(this.iterator(), other.iterator()).hasNext();
    }

    /**
     * @param iter source of sorted & distinct elements
     * @return a new instance of the {@link DynamicSet} subclass implementing this filled with
     *         elements from {@code iter}
     */
    protected abstract OrderedDynamicSet<E> fromSorted(Iterator<E> iter);

    /** @return a new empty instance of the {@link OrderedDynamicSet} */
    protected abstract OrderedDynamicSet<E> newEmptySet();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DynamicSet)) return false;
        DynamicSet<?> that = (DynamicSet<?>) obj;
        if (size() != that.size()) return false;
        return this.isSubsetOf(that);
    }

    @Override
    public int hashCode() {
        return this.stream().mapToInt(E::hashCode).sum();
    }

    @Override
    public String toString() {
        return "{" + join(", ", (Iterable<String>) this.stream().map(E::toString)::iterator) + "}";
    }
}
