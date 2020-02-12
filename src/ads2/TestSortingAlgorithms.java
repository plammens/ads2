package ads2;

import static ads2.utils.Utils.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ads2.sorters.*;


public class TestSortingAlgorithms {

    public static void main(String[] args) throws FileNotFoundException {
        List<Sorter> sorters = new ArrayList<>();
//        sorters.add(new InsertionSort());
        sorters.add(new QuickSort());
        sorters.add(new CutoffQuickSort(10));
        sorters.add(new MedianOfThreeQuickSort());
        sorters.add(new ThreeWayQuickSort());

        for (String path : args) {
            for (Sorter sorter : sorters) {
                int[] arr = toIntArray(readIntList(new FileInputStream(path)));
                sorter.sort(arr);
                System.out.print(sorter.getClass().getSimpleName() + ": ");
                System.out.println(isSorted(arr));
            }
        }
    }

}
