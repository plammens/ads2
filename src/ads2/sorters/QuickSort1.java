package ads2.sorters;

import java.util.List;

public class QuickSort1 extends BaseQuickSort {

    @Override
    protected <T extends Comparable<T>>
    void quicksort(List<T> arr, int p, int r) {
        if (p < r) {
            Partition<T> partition = partition(arr, p, r);
            quicksort(arr, p, q - 1);
            quicksort(arr, q + 1, r);
        }
    }

}
