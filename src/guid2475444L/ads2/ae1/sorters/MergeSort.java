package guid2475444L.ads2.ae1.sorters;

import guid2475444L.ads2.ae1.sorter.BaseSorter;


public class MergeSort extends BaseSorter {
    @Override
    public void sort(int[] arr) {
        mergesort(arr, 0, arr.length);
    }

    public void mergesort(int[] arr, int left, int right) {
        if (right - left <= 1) return;

        int mid = (left + right) / 2;
        mergesort(arr, left, mid);
        mergesort(arr, mid, right);
        merge(arr, left, mid, right);
    }

    public void merge(int[] arr, int left, int mid, int right) {
        int size = right - left;
        if (size <= 0) return;
        int[] merged = new int[size];  // merged copy

        for (int i = left, j = mid, k = 0; k < size; ++k) {
            if (j >= right || (i < mid && arr[i] < arr[j]))
                merged[k] = arr[i++];
            else
                merged[k] = arr[j++];
        }

        // copy back to original array
        System.arraycopy(merged, 0, arr, left, size);
    }
}
