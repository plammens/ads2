package ads2.sorters;

import static ads2.utils.Utils.swap;

import ads2.utils.ArrayRange;

public class ThreeWayQuickSort extends QuickSort {

    @Override
    protected Partition partition(int[] arr, int p, int r) {
        int pivot = arr[p];

        // i keeps track of the end of the first subarray (elements less than pivot)
        // j keeps track of the end of the second subarray (elements equal to pivot)
        int i = p, j = p + 1;
        for (int k = p + 1; k <= r; ++k) {
            if (arr[k] < pivot) swap(arr, k, i++);
            if (arr[k] == pivot) swap(arr, k, j++);
        }

        return new Partition(new ArrayRange(p, i), ArrayRange.inclusive(j, r));
    }

}
