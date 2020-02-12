package ads2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ads2.sorters.QuickSort1;
import ads2.sorters.Sorter;


public class TestSortingAlgorithms {

    public static List<Integer> readIntList(InputStream in) {
        Scanner sc = new Scanner(in);
        List<Integer> list = new ArrayList<Integer>();
        while (sc.hasNextInt())
            list.add(sc.nextInt());
        sc.close();
        return list;
    }

    public static boolean isSorted(List<Integer> arr) {
        for (int i = 1; i < arr.size(); ++i)
            if (arr.get(i - 1) > arr.get(i)) return false;
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Sorter sorter = new QuickSort1();

        for (String path : args) {
            List<Integer> arr = readIntList(new FileInputStream(path));
            sorter.sort(arr);
            System.out.println(isSorted(arr));
        }
    }

}
