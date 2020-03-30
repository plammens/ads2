package guid2475444L.ads2.ae2;

import java.util.Iterator;

import org.jetbrains.annotations.NotNull;


/**
 * Supertype for {@link DynamicSet} instances that maintain the order of their elements.
 * <p>
 * Subclasses implementing this must provide (efficient) stable sorted iteration through {@link
 * #iterator()}.
 * @param <E>
 */
public interface OrderedDynamicSet<E extends Comparable<E>> extends DynamicSet<E> {
    /** Provides sorted iteration through the elements of this set. */
    @Override
    @NotNull
    Iterator<E> iterator();
}
