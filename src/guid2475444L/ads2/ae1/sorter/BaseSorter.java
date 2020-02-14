package guid2475444L.ads2.ae1.sorter;

public abstract class BaseSorter implements Sorter {
    protected String initParams;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + (initParams == null ? "" : "(" + initParams + ")");
    }
}
