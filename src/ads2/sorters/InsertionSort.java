package ads2.sorters;

import java.util.List;

public class InsertionSort implements Sorter {

    public <T extends Comparable<T>> void sort(List<T> arr) {
        int n = arr.size();
        for (int j = 1; j < n; ++j) {
            T key = arr.get(j);
            int i = j - 1;
            for (; i >= 0 && arr.get(i).compareTo(key) > 0; --i)
                arr.set(i + 1, arr.get(i));
            arr.set(i + 1, key);
        }
    }

}
