package ads2.utils;

import java.util.List;

/** Various static utilities */
public class Utils {

    /**
     * Swap two elements of an array
     * @param arr array containing elements to swap
     * @param i   index of first element
     * @param j   index of second element
     */
    public static void swap(int[] arr, int i, int j) {
        int xi = arr[i];
        arr[i] = arr[j];
        arr[j] = xi;
    }

    /** Convert a {@link List<Integer>} to an {@code int[]} array */
    public static int[] toIntArray(List<Integer> arr) {
        int[] copy = new int[arr.size()];
        for (int i = 0; i < arr.size(); ++i)
            copy[i] = arr.get(i);
        return copy;
    }

    /** Determine whether an array is sorted */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; ++i)
            if (arr[i - 1] > arr[i]) return false;
        return true;
    }

}
