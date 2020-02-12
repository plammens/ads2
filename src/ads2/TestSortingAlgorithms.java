package ads2;

import static ads2.utils.Utils.isSorted;
import static ads2.utils.Utils.toIntArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ads2.sorters.Sorter;
import ads2.sorters.ThreeWayQuickSort;


public class TestSortingAlgorithms {

    public static List<Integer> readIntList(InputStream in) {
        Scanner sc = new Scanner(in);
        List<Integer> list = new ArrayList<Integer>();
        while (sc.hasNextInt())
            list.add(sc.nextInt());
        sc.close();
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Sorter sorter = new ThreeWayQuickSort();

        for (String path : args) {
            int[] arr = toIntArray(readIntList(new FileInputStream(path)));
            sorter.sort(arr);
            System.out.println(isSorted(arr));
        }
    }

}
