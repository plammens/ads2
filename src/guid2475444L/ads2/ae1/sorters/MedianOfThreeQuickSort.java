package guid2475444L.ads2.ae1.sorters;

import static guid2475444L.ads2.ae1.utils.Utils.swap;

import guid2475444L.ads2.ae1.utils.ArrayRange;

public class MedianOfThreeQuickSort extends QuickSort {

    @Override
    public Partition partition(int[] arr, int p, int r) {
        // shortcut for less than 3 elements
        if (r - p + 1 < 3) {
            if (arr[p] > arr[r]) swap(arr, p, r);
            return new Partition(new ArrayRange(p, r), new ArrayRange(r, r));
        }

        int mid = (p + r) / 2;
        // sort first, middle and last element:
        if (arr[p] > arr[mid]) swap(arr, p, mid);
        if (arr[p] > arr[r]) swap(arr, p, r);
        if (arr[mid] > arr[r]) swap(arr, mid, r);
        // get median:
        int pivot = arr[mid];

        swap(arr, mid, r - 1); // store pivot away
        int i = p + 1;
        for (int j = p + 1; j < r - 1; ++j)
            if (arr[j] <= pivot) swap(arr, i++, j);

        swap(arr, i, r - 1); // put pivot back in place
        return new Partition(new ArrayRange(p, i), ArrayRange.inclusive(i + 1, r));
    }

}
