package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


public abstract class DynamicSetTest extends CollectionTest {

    // --------------- Config ---------------

    @Override
    protected abstract @NotNull DynamicSet<Integer> constructTestSubject();

    @Override
    protected @NotNull DynamicSet<Integer> constructTestSubject(Collection<Integer> initializer) {
        return (DynamicSet<Integer>) super.constructTestSubject(initializer);
    }

    @Override
    protected abstract @NotNull DynamicSet<Integer> getTestSubject();

    DynamicSetTest() {
        super(List.of(1, 3, 3, 2, 4, 7, 6, 5, 9, 0, 8),  // init list
              List.of(-1, 11, new Object(), 13, 42),  // non-members sample
              10);  // init size
    }


    // --------------- Tests ---------------

    @Override
    @Test
    void add_Element_UpdatesCorrectly() {
        assertAll(INIT_LIST.stream().map((x) -> () -> assertFalse(testSubject.add(x))));
        assertPropertiesHaveNotChanged();

        List<Integer> toAdd =
                NON_MEMBERS_SAMPLE.stream().filter(Integer.class::isInstance)
                                  .map(Integer.class::cast).collect(Collectors.toList());
        for (Object elem : toAdd)
            if (elem instanceof Integer) assertTrue(testSubject.add((Integer) elem));
        assertEquals(INIT_SIZE + toAdd.size(), testSubject.size());
    }

    @Override
    @Test
    @SuppressWarnings("SuspiciousMethodCalls")
    void remove_Object_UpdatesCorrectly() {
        assertAll(NON_MEMBERS_SAMPLE.stream().map((x) -> () -> assertFalse(testSubject.remove(x))));
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
        DynamicSet<Integer> a = constructTestSubject(INIT_LIST), b = constructTestSubject(copy);
        assertTrue(a.equals(b));
        assertTrue(constructTestSubject().equals(constructTestSubject()));
    }

    @Test
    void union() {
        DynamicSet<Integer>
                a = constructTestSubject(List.of(1, 7, 3, 5, 1)),
                b = constructTestSubject(List.of(2, 1, 4, 7)),
                union = constructTestSubject(List.of(1, 2, 3, 4, 5, 7));
        assertEquals(union, a.union(b));
        assertEquals(union, DynamicSet.union(a, b));
    }

    @Test
    void intersect() {
        DynamicSet<Integer>
                a = constructTestSubject(List.of(1, 7, 3, 5, 1)),
                b = constructTestSubject(List.of(2, 1, 5, 4, 7, 8)),
                intersection = constructTestSubject(List.of(1, 5, 7));
        assertEquals(intersection, a.intersect(b));
        assertEquals(intersection, DynamicSet.intersection(a, b));
    }

    @Test
    void difference() {
        DynamicSet<Integer>
                a = constructTestSubject(List.of(1, 7, 2, 3, 5, 1)),
                b = constructTestSubject(List.of(1, 5, 4, 7, 8)),
                difference = constructTestSubject(List.of(2, 3));
        assertEquals(difference, a.minus(b));
        assertEquals(difference, DynamicSet.difference(a, b));
    }

    @Test
    void isSubsetOf() {
        DynamicSet<Integer>
                a = constructTestSubject(List.of(1, 7, 3, 5)),
                b = constructTestSubject(List.of(2, 1, 3, 5, 8, 7));
        assertTrue(a.isSubsetOf(b));
        assertFalse(b.isSubsetOf(a));
    }

    @Override
    @Test
    void testToString() {
        String string = testSubject.toString();
        assertEquals('{', string.charAt(0));
        assertEquals('}', string.charAt(string.length() - 1));
    }
}
