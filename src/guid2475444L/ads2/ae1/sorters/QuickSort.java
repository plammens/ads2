package guid2475444L.ads2.ae1.sorters;

import static guid2475444L.ads2.ae1.utils.Utils.swap;

import guid2475444L.ads2.ae1.Sorter;
import guid2475444L.ads2.ae1.utils.ArrayRange;


public class QuickSort implements Sorter {

    /** Tuple of array ranges representing a quicksort partition */
    protected static class Partition {

        public final ArrayRange first, second;

        public Partition(ArrayRange first, ArrayRange second) {
            this.first = first;
            this.second = second;
        }

    }

    @Override
    public void sort(int[] arr) {
        quicksort(arr);
    }

    /**
     * Top-level in-place quicksort (whole array)
     * <p>
     * ({@code arr} is guaranteed to be sorted afterwards)
     */
    protected void quicksort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    /**
     * Quick-sort on subarray arr[p..r]
     * <p>
     * (order not guaranteed as post-condition as it is implementation dependent—see e.g. {@link
     * CutoffQuickSort#quicksort(int[], int, int)})
     */
    protected void quicksort(int[] arr, int p, int r) {
        if (p < r) {
            Partition part = partition(arr, p, r);
            quicksort(arr, part.first.start, part.first.stop - 1);
            quicksort(arr, part.second.start, part.second.stop - 1);
        }
    }

    /**
     * After rearranging, return two index ranges {@code p..k, l..r} such that {@code a[p..k] <=
     * a[k+1..l-1] < a[l..r]}, so that they can be sorted recursively in {@link #quicksort(int[],
     * int, int)}.
     */
    protected Partition partition(int[] arr, int p, int r) {
        int pivot = arr[r];
        // note: starting from i = p instead of p - 1 to make code cleaner
        // (with appropriate changes)
        int i = p;
        for (int j = p; j < r; ++j)
            if (arr[j] <= pivot) swap(arr, i++, j);

        swap(arr, i, r);
        return new Partition(new ArrayRange(p, i), ArrayRange.inclusive(i + 1, r));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
