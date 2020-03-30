package guid2475444L.ads2.util;

/** Represents an contiguous index range for an array */
public class ArrayRange {

    /**
     * Array range from indices {@code first} to {@code last} (both inclusive)
     * @param first first index
     * @param last  last index
     */
    public static ArrayRange inclusive(int first, int last) {
        return new ArrayRange(first, last + 1);
    }

    public final int start, stop;

    /**
     * Array range from indices {@code start} (inclusive) to {@code stop} (exclusive)
     * @param start inclusive starting index
     * @param stop  exclusive end index
     */
    public ArrayRange(int start, int stop) {
        if (start < 0 || stop < 0)
            throw new IndexOutOfBoundsException("array index bounds must be non-negative");
        if (stop < start)
            throw new IndexOutOfBoundsException("start index should not be greater than stop");
        this.start = start;
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + stop + ")";
    }

}
