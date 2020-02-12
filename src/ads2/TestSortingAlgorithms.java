package ads2;

import static ads2.utils.Utils.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ads2.sorters.Sorter;
import ads2.sorters.ThreeWayQuickSort;


public class TestSortingAlgorithms {

    public static void main(String[] args) throws FileNotFoundException {
        Sorter sorter = new ThreeWayQuickSort();

        for (String path : args) {
            int[] arr = toIntArray(readIntList(new FileInputStream(path)));
            sorter.sort(arr);
            System.out.println(isSorted(arr));
        }
    }

}
