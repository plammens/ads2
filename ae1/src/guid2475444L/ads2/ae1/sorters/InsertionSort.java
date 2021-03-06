package guid2475444L.ads2.ae1.sorters;

import guid2475444L.ads2.ae1.sorter.BaseSorter;


public class InsertionSort extends BaseSorter {

    @Override
    public void sort(int[] arr) {
        for (int j = 1; j < arr.length; ++j) {
            int key = arr[j];
            int i = j - 1;
            for (; i >= 0 && arr[i] > key; --i)
                arr[i + 1] = arr[i];
            arr[i + 1] = key;
        }
    }

}
