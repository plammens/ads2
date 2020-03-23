package guid2475444L.ads2.ae2;

/**
 * Defines the dynamic set ADT.
 * <p>
 * The Dynamic Set is an abstract data type (ADT) that can store distinct elements, without any
 * particular order.
 * @param <T> type of the elements stored in this set
 */
public interface DynamicSet<T> {

    /**
     * Add an element to this set, if not present already.
     * @param x value of the element to be added
     */
    void add(T x);

    /**
     * Remove an element from this set, if present.
     * @param x value of the element to be removed
     */
    void remove(T x);

    /**
     * Returns true if this set contains the specified element. More formally, returns true if and
     * only if this set contains an element e such that {@code Objects.equals(o, e)}.
     * <p>
     * The reason we use {@link Object} as the parameter type is so we can deal with usages
     * involving polymorphism; e.g. when we have some objects whose runtime type we don't know and
     * we want to test which of them are in this set. Restricting the type of the parameter to
     * {@code T} would not allow that.
     * <p>
     * @param x object whose membership in this set is to be tested
     * @return whether {@code x} is an element of {@code this}
     */
    boolean contains(Object x);

    /** @return the number of elements in this set */
    int size();

    /** @return whether this set is empty */
    default boolean empty() {
        return size() == 0;
    }

    /**
     * Set union: elements that are in {@code this} or {@code other} (or both)
     * @param other set to unite with {@code this}
     * @return a new set consisting of the union of {@code this} and {@code other}
     */
    DynamicSet<T> union(DynamicSet<? extends T> other);

    /**
     * Set intersection: elements that are in both {@code this} and {@code other}
     * @param other set to intersect with {@code this}
     * @return a new set consisting of the intersection of {@code this} and {@code other}
     */
    DynamicSet<T> intersect(DynamicSet<? extends T> other);

    /**
     * Set difference: elements that are in {@code this} that are not in {@code other}
     * @param other set to unite with {@code this}
     * @return a new set consisting of the difference {@code this} minus {@code other}
     */
    DynamicSet<T> minus(DynamicSet<? extends T> other);

    /** @return whether {@code this} is a subset of {@code other} */
    boolean isSubsetOf(DynamicSet<?> other);

    /**
     * Alternative to {@code a.union(b)}
     * @see #union(DynamicSet)
     */
    static <U> DynamicSet<U> union(DynamicSet<? extends U> a, DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).union(b);
    }

    /**
     * Alternative to {@code a.intersect(b)}
     * @see #intersect
     */
    static <U> DynamicSet<U> intersection(DynamicSet<? extends U> a, DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).intersect(b);
    }

    /**
     * Alternative to {@code a.minus(b)}
     * @see #minus
     */
    static <U> DynamicSet<U> difference(DynamicSet<? extends U> a, DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).minus(b);
    }

}
