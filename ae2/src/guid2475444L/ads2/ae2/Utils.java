package guid2475444L.ads2.ae2;

import java.util.Collection;


/** Various static utilities */
public final class Utils {

    /**
     * Same as {@link Collection#contains(Object)} but with gentle handling of {@link
     * NullPointerException} and {@link ClassCastException} in case of element incompatibility
     */
    public static <E> boolean contains(Collection<E> c, Object elem) {
        try {
            //noinspection SuspiciousMethodCalls
            return c.contains(elem);
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
    }

}
