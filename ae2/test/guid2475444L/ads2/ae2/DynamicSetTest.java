package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


public abstract class DynamicSetTest extends CollectionTest {

    // --------------- Config ---------------

    @Override
    protected List<Integer> getInitList() {
        return List.of(1, 3, 3, 2, 4, 7, 6, 5, 9, 0, 8);
    }

    @Override
    protected List<Object> getNonMembersSample() {
        return List.of(-1, 11, 13, 42);
    }

    @Override
    protected int getInitSize() {
        return 10;
    }

    @Override
    protected abstract @NotNull DynamicSet<Integer> constructSubject();

    @Override
    protected @NotNull DynamicSet<Integer> constructSubject(Collection<Integer> initializer) {
        return (DynamicSet<Integer>) super.constructSubject(initializer);
    }

    @Override
    protected abstract @NotNull DynamicSet<Integer> getTestSubject();


    // --------------- Tests ---------------

    @Override
    @Test
    void add() {
        for (Integer elem : INIT_LIST) assertFalse(testSubject.add(elem));
        assertPropertiesHaveNotChanged();

        for (Object elem : NON_MEMBERS_SAMPLE)
            if (elem instanceof Integer) assertTrue(testSubject.add((Integer) elem));
        assertEquals(INIT_SIZE + NON_MEMBERS_SAMPLE.size(), testSubject.size());
    }

    @Override
    @Test
    @SuppressWarnings("SuspiciousMethodCalls")
    void remove() {
        for (Object elem : NON_MEMBERS_SAMPLE) assertFalse(testSubject.remove(elem));
        assertPropertiesHaveNotChanged();

        assertTrue(testSubject.remove(0));
        assertTrue(testSubject.remove(3));
        assertFalse(testSubject.remove(3));
        assertEquals(INIT_SIZE - 2, testSubject.size());
    }

    @Override
    @Test
    @SuppressWarnings({"SimplifiableJUnitAssertion", "EqualsWithItself"})
    void testEquals() {
        assertTrue(testSubject.equals(testSubject));
        assertFalse(testSubject.equals(INIT_LIST));

        List<Integer> copy = new ArrayList<>(INIT_LIST);
        Collections.reverse(copy);
        DynamicSet<Integer> a = constructSubject(INIT_LIST), b = constructSubject(copy);
        assertTrue(a.equals(b));
        assertTrue(constructSubject().equals(constructSubject()));
    }

    @Test
    void union() {
        DynamicSet<Integer>
                a = constructSubject(List.of(1, 7, 3, 5, 1)),
                b = constructSubject(List.of(2, 1, 4, 7)),
                union = constructSubject(List.of(1, 2, 3, 4, 5, 7));
        assertEquals(union, a.union(b));
        assertEquals(union, DynamicSet.union(a, b));
    }

    @Test
    void intersect() {
        DynamicSet<Integer>
                a = constructSubject(List.of(1, 7, 3, 5, 1)),
                b = constructSubject(List.of(2, 1, 5, 4, 7, 8)),
                intersection = constructSubject(List.of(1, 5, 7));
        assertEquals(intersection, a.intersect(b));
        assertEquals(intersection, DynamicSet.intersection(a, b));
    }

    @Test
    void difference() {
        DynamicSet<Integer>
                a = constructSubject(List.of(1, 7, 2, 3, 5, 1)),
                b = constructSubject(List.of(1, 5, 4, 7, 8)),
                difference = constructSubject(List.of(2, 3));
        assertEquals(difference, a.minus(b));
        assertEquals(difference, DynamicSet.difference(a, b));
    }

    @Test
    void isSubsetOf() {
        DynamicSet<Integer>
                a = constructSubject(List.of(1, 7, 3, 5)),
                b = constructSubject(List.of(2, 1, 3, 5, 8, 7));
        assertTrue(a.isSubsetOf(b));
        assertFalse(b.isSubsetOf(a));
    }

    @Override
    @Test
    void containsAll() {
    }

    @Override
    @Test
    void addAll() {
    }

    @Override
    @Test
    void removeAll() {
    }

    @Override
    @Test
    void retainAll() {
    }

    @Override
    @Test
    void iterator() {
    }

    @Override
    @Test
    void toArray() {

    }

    @Override
    @Test
    void testToString() {
        String string = testSubject.toString();
        assertEquals('{', string.charAt(0));
        assertEquals('}', string.charAt(string.length() - 1));
    }
}
