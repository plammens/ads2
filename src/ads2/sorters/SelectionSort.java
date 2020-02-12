package ads2.sorters;

import java.util.List;

public class SelectionSort implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> arr) {
        int n = arr.size();
        
        for (int i = 0; i < n; ++i) {
            int minIndex = i;
            T minElement = arr.get(i);
            for (int j = i + 1; j < n; ++j) {
                T elem = arr.get(j);
                if (elem.compareTo(minElement) < 0) {
                    minIndex = j;
                    minElement = elem;
                }
            }
            T swapped = arr.get(i);
            arr.set(i, minElement);
            arr.set(minIndex, swapped);
        }
    }

}
