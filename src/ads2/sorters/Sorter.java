package ads2.sorters;

import java.util.ArrayList;
import java.util.List;

public interface Sorter {

    /** In-place sort */
    <T extends Comparable<T>>
    void sort(List<T> arr);

    /** Sorted copy */
    default <T extends Comparable<T>>
    List<T> sorted(List<T> arr) {
        List<T> copy = new ArrayList<T>(arr.size());
        copy.addAll(arr);
        sort(copy);
        return copy;
    }

}
