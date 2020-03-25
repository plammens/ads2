package guid2475444L.ads2.ae1;

import static guid2475444L.ads2.ae1.utils.Utils.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import guid2475444L.ads2.ae1.sorter.Sorter;
import guid2475444L.ads2.ae1.sorters.*;


public class TestSortingAlgorithms {

    public static void main(String[] args) throws FileNotFoundException {
        List<Sorter> sorters = new ArrayList<>();
        sorters.add(new InsertionSort());
        sorters.add(new QuickSort());
        sorters.add(new CutoffQuickSort(10));
        sorters.add(new MedianOfThreeQuickSort());
        sorters.add(new ThreeWayQuickSort());
        sorters.add(new MergeSort());

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
