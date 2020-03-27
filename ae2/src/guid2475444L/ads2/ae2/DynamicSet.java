package guid2475444L.ads2.ae2;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;


/**
 * Defines the dynamic set ADT.
 * <p>
 * The Dynamic Set is an abstract data type (ADT) that can store distinct elements, without any
 * particular order. The elements must implement {@link Comparable} nevertheless so that certain
 * implementations can be possible.
 * <p>
 * Note: this interface extends {@link Collection} just because it defines useful methods and so
 * that implementing objects can be passed to functions with a Collection as parameter, but all the
 * relevant operations have been redefined here in {@link DynamicSet}.
 * <p>
 * @param <E> type of the elements stored in this set
 * @author - Paolo Lammens (2475444L)
 */
public interface DynamicSet<E extends Comparable<E>> extends Collection<E> {

    /**
     * Add an element to this set, if not present already.
     * @param x value of the element to be added
     */
    boolean add(E x);

    /**
     * Remove an element from this set, if present.
     * @param x value of the element to be removed
     */
    boolean remove(Object x);

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
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Set union: elements that are in {@code this} or {@code other} (or both)
     * @param other set to unite with {@code this}
     * @return a new set consisting of the union of {@code this} and {@code other}
     */
    DynamicSet<E> union(@NotNull DynamicSet<? extends E> other);

    /**
     * Set intersection: elements that are in both {@code this} and {@code other}
     * @param other set to intersect with {@code this}
     * @return a new set consisting of the intersection of {@code this} and {@code other}
     */
    DynamicSet<E> intersect(@NotNull DynamicSet<? extends E> other);

    /**
     * Set difference: elements that are in {@code this} that are not in {@code other}
     * @param other set to unite with {@code this}
     * @return a new set consisting of the difference {@code this} minus {@code other}
     */
    DynamicSet<E> minus(@NotNull DynamicSet<? extends E> other);

    /** @return whether {@code this} is a subset of {@code other} */
    boolean isSubsetOf(@NotNull DynamicSet<?> other);

    /**
     * Alternative to {@code a.union(b)}
     * @see #union(DynamicSet)
     */
    static <U extends Comparable<U>> DynamicSet<U> union(@NotNull DynamicSet<? extends U> a,
                                                         @NotNull DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).union(b);
    }

    /**
     * Alternative to {@code a.intersect(b)}
     * @see #intersect
     */
    static <U extends Comparable<U>> DynamicSet<U> intersection(@NotNull DynamicSet<? extends U> a,
                                                                @NotNull DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).intersect(b);
    }

    /**
     * Alternative to {@code a.minus(b)}
     * @see #minus
     */
    static <U extends Comparable<U>> DynamicSet<U> difference(@NotNull DynamicSet<? extends U> a,
                                                              @NotNull DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).minus(b);
    }

}
