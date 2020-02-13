package ads2;

import static ads2.utils.Utils.readIntList;
import static ads2.utils.Utils.toIntArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import ads2.sorters.*;


public class TimeSortingAlgorithms {

    public static final Sorter[] SORTERS = new Sorter[]{
            new QuickSort(),
            new CutoffQuickSort(3),
            new CutoffQuickSort(50),
            new CutoffQuickSort(200),
            new MedianOfThreeQuickSort(),
            new ThreeWayQuickSort(),
    };

    /**
     * Time a sorting algorithm on an array
     * @param sorter sorter object to use
     * @param arr    array to sort
     * @return time elapsed to execute {@code sorter.sort(arr);}
     */
    public static Duration timeSorter(Sorter sorter, int[] arr) {
        Instant start = Instant.now();
        sorter.sort(arr);
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Timing " + Arrays.toString(SORTERS)
                + " against " + Arrays.toString(args) + "\n");

        for (String path : args) {
            System.out.println("---------------------------------------------");
            System.out.println("INPUT: " + path + "\n");
            for (Sorter sorter : SORTERS) {
                int[] arr = toIntArray(readIntList(new FileInputStream(path)));
                Duration t = timeSorter(sorter, arr);
                System.out.println(sorter + ": " + t.getSeconds() + "s " + t.getNano() + "ns");
            }
            System.out.println();
        }
    }

}
