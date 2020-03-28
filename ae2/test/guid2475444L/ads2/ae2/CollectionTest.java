package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@SuppressWarnings("ConstantConditions")
public abstract class CollectionTest {

    // --------------- Config ---------------

    protected final List<Integer> INIT_LIST;
    protected final List<Object> NON_MEMBERS_SAMPLE;
    protected final int INIT_SIZE;
    protected Collection<Integer> testSubject;

    /**
     * Configure a test suite for a {@link Collection} subclass
     * @param initList         initializer list to fill the test subject with
     * @param nonMembersSample a sample of elements that are not supposed to be in the tested
     *                         collection after initialisation with {@code constructSubject
     *                         (getInitList())}
     * @param initSize         the initial size that the tested collection instance is supposed to
     *                         have after initialisation
     */
    public CollectionTest(List<Integer> initList, List<Object> nonMembersSample, int initSize) {
        this.INIT_LIST = initList;
        this.NON_MEMBERS_SAMPLE = nonMembersSample;
        this.INIT_SIZE = initSize;
    }

    /** @return an empty instance of the collection class to be tested */
    protected abstract @NotNull Collection<Integer> constructSubject();

    /** @return an instance of the class to be tested filled with elements from a collection */
    protected @NotNull Collection<Integer> constructSubject(Collection<Integer> initializer) {
        Collection<Integer> testSubject = constructSubject();
        testSubject.addAll(initializer);
        return testSubject;
    }

    /** @return the test subject of this test suite */
    protected abstract @NotNull Collection<Integer> getTestSubject();

    @BeforeEach
    void setUp() {
        testSubject = constructSubject(INIT_LIST);
    }


    // --------------- Tests ---------------

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    void contains() {
        for (Integer elem : INIT_LIST) assertTrue(testSubject.contains(elem));
        for (Object elem : NON_MEMBERS_SAMPLE) assertFalse(testSubject.contains(elem));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void size() {
        assertEquals(INIT_SIZE, testSubject.size());
    }

    @Test
    void isEmpty() {
        assertFalse(testSubject.isEmpty());
        testSubject.clear();
        assertTrue(testSubject.isEmpty());
    }

    @Test
    void clear() {
        testSubject.clear();
        assertEquals(0, testSubject.size());
        assertTrue(testSubject.isEmpty());
    }

    @Test
    abstract void add();

    @Test
    abstract void remove();

    @Test
    abstract void containsAll();

    @Test
    abstract void addAll();

    @Test
    abstract void removeAll();

    @Test
    abstract void retainAll();

    @Test
    abstract void iterator();

    @Test
    abstract void toArray();

    @Test
    abstract void testEquals();

    @Test
    abstract void testToString();

    void assertPropertiesHaveNotChanged() {
        assertEquals(INIT_SIZE, testSubject.size());
        assertFalse(testSubject.isEmpty());
    }

}
