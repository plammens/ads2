package ads2.sorters;

import java.util.List;

/** @see QuickSort2#quicksort(List, int, int) */
public class QuickSort2 extends BaseQuickSort {

    /** Quicksort recursion will end at sub-arrays of this size or smaller */
    private final int baseArraySize;

    QuickSort2(int baseArraySize) {
        this.baseArraySize = baseArraySize;
    }

    /** @see #quicksort(List, int, int) */
    @Override
    protected <T extends Comparable<T>> void quicksort(List<T> arr) {
        super.quicksort(arr);  // incomplete quick-sort
        new InsertionSort().sort(arr);  // complete with insertion sort
    }

    /**
     * @implNote recursion stops at subarrays of size less than {@link #baseArraySize},
     *         yielding an incomplete sort (which is completed at the top level call {@link
     *         #quicksort(List)} with an {@link InsertionSort})
     */
    @Override
    protected <T extends Comparable<T>>
    void quicksort(List<T> arr, int p, int r) {
        if (p < r && (r - p + 1) >= baseArraySize) {
            int q = partition(arr, p, r);
            quicksort(arr, p, q - 1);
            quicksort(arr, q + 1, r);
        }
    }

}
