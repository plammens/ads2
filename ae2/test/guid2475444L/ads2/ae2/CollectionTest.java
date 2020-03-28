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

    protected final List<Integer> INIT_LIST = getInitList();
    protected final List<Object> NON_MEMBERS_SAMPLE = getNonMembersSample();
    protected final int INIT_SIZE = getInitSize();
    protected Collection<Integer> testSubject;

    /** factory for the initializer list to fill the test subject with */
    protected abstract List<Integer> getInitList();

    /**
     * @return factory for a sample of elements that are not supposed to be in the tested collection
     *         after initialisation with {@code constructSubject(getInitList())}
     * @see #getInitList()
     */
    protected abstract List<Object> getNonMembersSample();

    /** get the initial size that the test subject is supposed to have after initialisation */
    protected abstract int getInitSize();

    /** @return an empty instance of the collection class to be tested */
    protected abstract @NotNull Collection<Integer> constructSubject();

    /** @return an instance of the class to be tested filled with elements from a collection */
    protected @NotNull Collection<Integer> constructSubject(Collection<Integer> initializer) {
        Collection<Integer> testSubject = constructSubject();
        testSubject.addAll(initializer);
        return testSubject;
    }

    /** @return the test subject of this test (an instance of a {@link Collection} subclass) */
    protected abstract Collection<Integer> getTestSubject();

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
