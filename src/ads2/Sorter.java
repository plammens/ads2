package ads2;

import java.util.Arrays;

public interface Sorter {

    /** In-place sort */
    void sort(int[] arr);

    /** Return a sorted copy */
    default int[] sorted(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        sort(copy);
        return copy;
    }

}
