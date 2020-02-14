package guid2475444L.ads2.ae1.sorters;

/** @see CutoffQuickSort#quicksort(int[], int, int) */
public class CutoffQuickSort extends QuickSort {

    /** quicksort recursion will end at sub-arrays of this size or smaller */
    private final int cutoffSize;

    /**
     * Construct a new quicksorter with a certain cutoff size
     * @param cutoffSize quicksort recursion will end at sub-arrays of this size or smaller
     *         (the sorting will be then completed with {@link InsertionSort})
     */
    public CutoffQuickSort(int cutoffSize) {
        this.cutoffSize = cutoffSize;
    }

    /**
     * @implNote after applying quicksort recursively down to the {@link #cutoffSize}, the
     *         sorting is completed with an {@link InsertionSort}
     * @see #quicksort(int[], int, int)
     */
    @Override
    protected final void quicksort(int[] arr) {
        super.quicksort(arr);  // incomplete quick-sort
        new InsertionSort().sort(arr);  // complete with insertion sort
    }

    /** @implNote recursion stops at subarrays of size less than {@link #cutoffSize} */
    @Override
    protected void quicksort(int[] arr, int p, int r) {
        if ((r - p + 1) >= cutoffSize) super.quicksort(arr, p, r);
    }

    @Override
    public String toString() {
        return super.toString() + "(cutoffSize=" + cutoffSize + ")";
    }

}
