package guid2475444L.ads2.ae2;

import static java.lang.String.join;


/**
 * Provides a default implementation for some {@link Object} methods for {@link DynamicSet}
 * implementors to inherit
 * @param <E> type of elements
 * @see DynamicSet
 */
public abstract class AbstractDynamicSet<E extends Comparable<E>> implements DynamicSet<E> {
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
