package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@SuppressWarnings("ConstantConditions")
public abstract class DynamicSetTest {
    final static List<Integer> INIT_LIST = List.of(1, 3, 3, 2, 4, 7, 6, 5, 9, 0, 8);
    final static int INIT_SIZE = 10;
    public static final List<Integer> NON_MEMBERS_SAMPLE = List.of(-1, 11, 13, 42);

    /** dynamic set to be tested */
    protected DynamicSet<Integer> set;

    /** create an empty instance of the set class to be tested */
    @NotNull
    protected abstract DynamicSet<Integer> constructSet();

    /** create an instance of the set class to be tested filled with elements from a collection */
    @NotNull
    protected DynamicSet<Integer> constructSet(Collection<Integer> initializer) {
        DynamicSet<Integer> set = constructSet();
        set.addAll(initializer);
        return set;
    }

    @BeforeEach
    void setUp() {
        set = constructSet(INIT_LIST);
    }

    @Test
    void add() {
        for (Integer elem : INIT_LIST) assertFalse(set.add(elem));
        assertPropertiesHaveNotChanged();

        for (Integer elem : NON_MEMBERS_SAMPLE) assertTrue(set.add(elem));
        assertEquals(INIT_SIZE + NON_MEMBERS_SAMPLE.size(), set.size());
    }

    @Test
    void remove() {
        for (Integer elem : NON_MEMBERS_SAMPLE) assertFalse(set.remove(elem));
        assertPropertiesHaveNotChanged();

        assertTrue(set.remove(0));
        assertTrue(set.remove(3));
        assertFalse(set.remove(3));
        assertEquals(INIT_SIZE - 2, set.size());
    }

    @Test
    void contains() {
        Assertions.assertFalse(set.contains(-1));
        Assertions.assertFalse(set.contains(11));
        for (Integer elem : INIT_LIST) Assertions.assertTrue(set.contains(elem));
        assertPropertiesHaveNotChanged();
    }

    @Test
    void size() {
        assertEquals(INIT_SIZE, set.size());
    }

    @Test
    void isEmpty() {
        Assertions.assertFalse(set.isEmpty());
        set.clear();
        Assertions.assertTrue(set.isEmpty());
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "EqualsWithItself",
            "EqualsBetweenInconvertibleTypes"})
    @Test
    void testEquals() {
        assertTrue(set.equals(set));
        assertFalse(set.equals(INIT_LIST));

        List<Integer> copy = new ArrayList<>(INIT_LIST);
        Collections.reverse(copy);
        DynamicSet<Integer> a = constructSet(INIT_LIST), b = constructSet(copy);
        assertTrue(a.equals(b));
        assertTrue(constructSet().equals(constructSet()));
    }

    @Test
    void union() {
        DynamicSet<Integer>
                a = constructSet(List.of(1, 7, 3, 5, 1)),
                b = constructSet(List.of(2, 1, 4, 7)),
                union = constructSet(List.of(1, 2, 3, 4, 5, 7));
        assertEquals(union, a.union(b));
        assertEquals(union, DynamicSet.union(a, b));
    }

    @Test
    void intersect() {
        DynamicSet<Integer>
                a = constructSet(List.of(1, 7, 3, 5, 1)),
                b = constructSet(List.of(2, 1, 5, 4, 7, 8)),
                intersection = constructSet(List.of(1, 5, 7));
        assertEquals(intersection, a.intersect(b));
        assertEquals(intersection, DynamicSet.intersection(a, b));
    }

    @Test
    void difference() {
        DynamicSet<Integer>
                a = constructSet(List.of(1, 7, 2, 3, 5, 1)),
                b = constructSet(List.of(1, 5, 4, 7, 8)),
                difference = constructSet(List.of(2, 3));
        assertEquals(difference, a.minus(b));
        assertEquals(difference, DynamicSet.difference(a, b));
    }

    @Test
    void isSubsetOf() {
        DynamicSet<Integer>
                a = constructSet(List.of(1, 7, 3, 5)),
                b = constructSet(List.of(2, 1, 3, 5, 8, 7));
        assertTrue(a.isSubsetOf(b));
        assertFalse(b.isSubsetOf(a));
    }

    @Test
    void clear() {
        set.clear();
        assertTrue(set.isEmpty());
    }

    @Test
    void containsAll() {
    }

    @Test
    void addAll() {
    }

    @Test
    void removeAll() {
    }

    @Test
    void retainAll() {
    }

    @Test
    void iterator() {
    }

    @Test
    void toArray() {
    }

    @Test
    void testToArray() {
    }

    void assertPropertiesHaveNotChanged() {
        Assertions.assertEquals(INIT_SIZE, set.size());
        Assertions.assertFalse(set.isEmpty());
    }
}
