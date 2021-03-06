package guid2475444L.ads2.ae2;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;


/**
 * Defines the dynamic set ADT.
 * <p>
 * The Dynamic Set is an abstract data type (ADT) that can store distinct elements, without any
 * particular order.
 * <p>
 * Cannot contain {@code null} values.
 * <p>
 * Note: this interface extends {@link Collection} just because it defines useful methods and so
 * that implementing objects can be passed to functions with a Collection as parameter, but all the
 * relevant operations have been redefined here in {@link DynamicSet}.
 * <p>
 * @param <E> type of the elements stored in this set
 * @author - Paolo Lammens (2475444L)
 */
public interface DynamicSet<E> extends Collection<E> {

    /**
     * Add an element to this set, if not present already.
     * @param x value of the element to be added
     */
    @Override
    boolean add(E x);

    /**
     * Remove an element from this set, if present.
     * @param x value of the element to be removed
     */
    @Override
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
     * Returns {@code false} if {@code x} is {@code null} or of an incompatible type.
     * @param x object whose membership in this set is to be tested
     * @return whether {@code x} is an element of {@code this}
     */
    @Override
    boolean contains(Object x);

    /** @return the number of elements in this set */
    @Override
    int size();

    /** @return whether this set is empty */
    @Override
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
     * Compares the specified object with this set for equality.  Returns {@code true} if the
     * specified object is also a set, the two sets have the same size, and every member of the
     * specified set is contained in this set (or equivalently, every member of this set is
     * contained in the specified set).  This definition ensures that the equals method works
     * properly across different implementations of the set interface.
     * @param obj object to be compared for equality with this set
     * @return {@code true} if the specified object is equal to this set
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns the hash code value for this set.  The hash code of a set is defined to be the sum of
     * the hash codes of the elements in the set, where the hash code of a {@code null} element is
     * defined to be zero. This ensures that {@code s1.equals(s2)} implies that {@code
     * s1.hashCode()==s2.hashCode()} for any two sets {@code s1} and {@code s2}, as required by the
     * general contract of {@link Object#hashCode}.
     * @return the hash code value for this set
     * @see DynamicSet#equals(Object)
     */
    int hashCode();

    /**
     * Alternative to {@code a.union(b)}
     * @see #union(DynamicSet)
     */
    static <U> DynamicSet<U> union(@NotNull DynamicSet<? extends U> a,
                                   @NotNull DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).union(b);
    }

    /**
     * Alternative to {@code a.intersect(b)}
     * @see #intersect
     */
    static <U> DynamicSet<U> intersection(@NotNull DynamicSet<? extends U> a,
                                          @NotNull DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).intersect(b);
    }

    /**
     * Alternative to {@code a.minus(b)}
     * @see #minus
     */
    static <U> DynamicSet<U> difference(@NotNull DynamicSet<? extends U> a,
                                        @NotNull DynamicSet<? extends U> b) {
        //noinspection unchecked
        return ((DynamicSet<U>) a).minus(b);
    }

}
