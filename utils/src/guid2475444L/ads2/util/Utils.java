package guid2475444L.ads2.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class Utils {
    public static List<Integer> readIntList(InputStream in) {
        Scanner sc = new Scanner(in);
        List<Integer> list = new ArrayList<>();
        while (sc.hasNextInt())
            list.add(sc.nextInt());
        sc.close();
        return list;
    }

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

    /**
     * Concatenate two lists
     * @param a first list
     * @param b second list
     * @return a new list consisting of the elements of {@code a} followed by the elements of {@code
     *         b} in their respective orders
     */
    public static <E> List<E> concat(List<? extends E> a, List<? extends E> b) {
        return Stream.concat(a.stream(), b.stream()).collect(Collectors.toList());
    }
}
