package guid2475444L.ads2.ae2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


public abstract class OrderedDynamicSetTest extends DynamicSetTest {
    // --------------- Config ---------------

    @Override
    protected abstract @NotNull OrderedDynamicSet<Integer> constructTestSubject();

    @Override
    protected abstract @NotNull OrderedDynamicSet<Integer> getTestSubject();


    // --------------- Tests ---------------

    @Override
    @Test
    void iterator() {
        assertArrayEquals(INIT_LIST.stream().distinct().sorted().toArray(), testSubject.toArray());
    }

    @Override
    @Test
    void remove_Object_UpdatesCorrectly() {
        super.remove_Object_UpdatesCorrectly();
        assertArrayEquals(List.of(2, 3, 4, 5, 6, 8).toArray(), getTestSubject().toArray());
    }
}
