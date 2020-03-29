package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@SuppressWarnings({"ConstantConditions", "SuspiciousMethodCalls",
        "RedundantOperationOnEmptyContainer"})
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
     *                         collection after initialisation with {@code constructTestSubject
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
    protected abstract @NotNull Collection<Integer> constructTestSubject();

    /** @return an instance of the class to be tested filled with elements from a collection */
    protected @NotNull Collection<Integer> constructTestSubject(Collection<Integer> initializer) {
        Collection<Integer> testSubject = constructTestSubject();
        testSubject.addAll(initializer);
        return testSubject;
    }

    /** @return the test subject of this test suite */
    protected abstract @NotNull Collection<Integer> getTestSubject();

    @BeforeEach
    void setUp() {
        testSubject = constructTestSubject(INIT_LIST);
    }


    // --------------- Tests ---------------

    @Test
    final void contains_InitElements_ReturnsTrue() {
        assertAll(INIT_LIST.stream().map((x) -> () -> assertTrue(testSubject.contains(x))));
        assertPropertiesHaveNotChanged();
    }

    @Test
    @SuppressWarnings("SuspiciousMethodCalls")
    final void contains_NonMembers_ReturnsFalse() {
        assertAll(NON_MEMBERS_SAMPLE.stream().map((x) -> () -> assertFalse(testSubject.contains(x))));
        assertPropertiesHaveNotChanged();
    }

    @Test
    final void size_Init_CorrectSize() {
        assertEquals(INIT_SIZE, testSubject.size());
        assertPropertiesHaveNotChanged();
    }

    @Test
    final void isEmpty_Full_ReturnsFalse() {
        assertFalse(testSubject.isEmpty());
        assertPropertiesHaveNotChanged();
    }

    @Test
    final void isEmpty_AfterClear_ReturnsTrue() {
        testSubject.clear();
        assertTrue(testSubject.isEmpty());
    }

    @Test
    final void clear_Full_IsEmpty() {
        testSubject.clear();
        assertEquals(0, testSubject.size());
        assertTrue(testSubject.isEmpty());
        assertFalse(testSubject.iterator().hasNext());
    }

    @Test
    abstract void add_Element_UpdatesCorrectly();

    @Test
    abstract void remove_Object_UpdatesCorrectly();

    @Test
    final void containsAll() {
        assertTrue(testSubject.containsAll(INIT_LIST));
        assertFalse(testSubject.containsAll(NON_MEMBERS_SAMPLE));
        assertFalse(testSubject.containsAll(Utils.concat(INIT_LIST, NON_MEMBERS_SAMPLE)));
    }

    @Test
    final void addAll_DistinctNonMembers_AllGetAdded() {
        List<Integer> toAdd = NON_MEMBERS_SAMPLE.stream().filter(Integer.class::isInstance)
                                                .map(Integer.class::cast).distinct()
                                                .collect(Collectors.toList());
        assertTrue(testSubject.addAll(toAdd));
        assertTrue(testSubject.containsAll(toAdd));
        assertEquals(INIT_SIZE + toAdd.size(), testSubject.size());
    }

    @Test
    final void removeAll_NonMembers_DoesNotChange() {
        assertFalse(testSubject.removeAll(NON_MEMBERS_SAMPLE));
        assertPropertiesHaveNotChanged();
    }

    @Test
    final void removeAll_ElementSequence_NoneIsElementAnymore() {
        List<Integer> toRemove =
                INIT_LIST.stream().limit(INIT_SIZE / 2).collect(Collectors.toList());
        assertTrue(testSubject.removeAll(toRemove));
        assertTrue(toRemove.stream().noneMatch(testSubject::contains));
    }

    @Test
    final void removeAll_InitElements_IsEmpty() {
        assertTrue(testSubject.removeAll(INIT_LIST));
        assertTrue(testSubject.isEmpty());
    }

    @Test
    final void retainAll_InitElements_DoesNotChange() {
        assertFalse(testSubject.retainAll(INIT_LIST));
        assertPropertiesHaveNotChanged();
    }

    @Test
    abstract void iterator();

    @Test
    final void toArray_NoArgs_ArrayHasEquivalentProperties() {
        Object[] arr = testSubject.toArray();
        assertEquals(INIT_SIZE, arr.length);
        assertTrue(Arrays.asList(arr).containsAll(testSubject));
        assertPropertiesHaveNotChanged();
    }

    @Test
    final void toArray_Oversized_ElementFollowingLastIsNull() {
        Integer[] oversized = new Integer[INIT_SIZE + 2];
        testSubject.toArray(oversized);
        assertNull(oversized[INIT_SIZE]);
        assertPropertiesHaveNotChanged();
    }

    @Test
    final void toArray_EmptySupertypeArray_FillsNewArrayWithGivenType() {
        Number[] arrArg = new Number[0];
        Object[] returnedArr = testSubject.toArray(arrArg);
        assertEquals(Number.class, returnedArr.getClass().getComponentType());
        assertEquals(testSubject.size(), returnedArr.length);
        assertTrue(Arrays.asList(returnedArr).containsAll(testSubject));
        assertPropertiesHaveNotChanged();
    }

    @Test
    abstract void testEquals();

    @Test
    abstract void testToString();

    void assertPropertiesHaveNotChanged() {
        assertEquals(INIT_SIZE, testSubject.size());
        assertFalse(testSubject.isEmpty());
    }

}
