package ads2.sorters;

/** @see CutoffQuickSort#quicksort(int[], int, int) */
public class CutoffQuickSort extends QuickSort {

    /** Quicksort recursion will end at sub-arrays of this size or smaller */
    private final int cutoffSize;

    CutoffQuickSort(int cutoffSize) {
        this.cutoffSize = cutoffSize;
    }

    /** @see #quicksort(int[], int, int) */
    @Override
    protected final void quicksort(int[] arr) {
        super.quicksort(arr);  // incomplete quick-sort
        new InsertionSort().sort(arr);  // complete with insertion sort
    }

    /**
     * @implNote recursion stops at subarrays of size less than {@link #cutoffSize},
     *         yielding an incomplete sort (which is completed at the top level call {@link
     *         #quicksort(int[])} with an {@link InsertionSort})
     */
    @Override
    protected void quicksort(int[] arr, int p, int r) {
        if ((r - p + 1) >= cutoffSize) super.quicksort(arr, p, r);
    }

    @Override
    public String toString() {
        return super.toString() + "(cutoffSize=" + cutoffSize + ")";
    }

}
