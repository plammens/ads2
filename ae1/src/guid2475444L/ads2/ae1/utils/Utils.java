package guid2475444L.ads2.ae1.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/** Various static utilities */
public final class Utils {

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

    /** Determine whether an array is sorted */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; ++i)
            if (arr[i - 1] > arr[i]) return false;
        return true;
    }

    /** Convert a {@link List<Integer>} to an {@code int[]} array */
    public static int[] toIntArray(List<Integer> arr) {
        int[] copy = new int[arr.size()];
        for (int i = 0; i < arr.size(); ++i)
            copy[i] = arr.get(i);
        return copy;
    }

    public static List<Integer> readIntList(InputStream in) {
        Scanner sc = new Scanner(in);
        List<Integer> list = new ArrayList<>();
        while (sc.hasNextInt())
            list.add(sc.nextInt());
        sc.close();
        return list;
    }

}
