package ads2.sorters;

import java.util.List;

public abstract class BaseQuickSort implements Sorter {

    /** Tuple of two subarrays (views on the original list)—represents a quicksort partition */
    protected static class Partition<T extends Comparable<T>> {
        public final List<T> first;
        public final List<T> second;

        public Partition(List<T> first, List<T> second) {
            this.first = first;
            this.second = second;
        }
    }

    @Override
    public <T extends Comparable<T>>
    void sort(List<T> arr) {
        quicksort(arr, 0, arr.size() - 1);
    }

    /**
     * Top-level in-place quicksort (whole array)
     * <p>
     * (arr is guaranteed to be sorted afterwards)
     */
    protected <T extends Comparable<T>>
    void quicksort(List<T> arr) {
        quicksort(arr, 0, arr.size() - 1);
    }

    /**
     * In-place quicksort between indices `p` and `r`
     * <p>
     * (sorting not guaranteed as post-condition as it is implementation dependent)
     */
    protected abstract <T extends Comparable<T>>
    void quicksort(List<T> arr, int p, int r);

    /**
     * After rearranging, return two subarrays {@code arr[p..k], arr[l..r]} (views, not copies) such
     * that {@code a[p..k] <= a[k+1..l-1] < a[l..r]}, so that they can be sorted recursively in
     * {@link #quicksort(List, int, int)}.
     */
    protected <T extends Comparable<T>>
    Partition<T> partition(List<T> arr, int p, int r) {
        T pivot = arr.get(r);

        // note: starting from i = p instead of p - 1 to make code cleaner
        // (with appropriate changes)
        int i = p;
        for (int j = p; j < r; ++j) {
            T xj = arr.get(j);
            if (xj.compareTo(pivot) <= 0) {
                arr.set(j, arr.get(i));
                arr.set(i, xj);
                ++i;
            }
        }
        arr.set(r, arr.get(i));
        arr.set(i, pivot);
        return new Partition<T>(arr.subList(p, i), arr.subList(i + 1, r));
    }

}
