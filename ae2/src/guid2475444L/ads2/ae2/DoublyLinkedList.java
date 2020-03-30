package guid2475444L.ads2.ae2;

import static java.lang.String.format;

import java.lang.reflect.Array;
import java.util.*;

import guid2475444L.ads2.util.Utils;
import org.jetbrains.annotations.NotNull;


/**
 * Implementation for a doubly linked list
 * <p>
 * Supports all operations specified in {@link java.util.List}, and allows all element values
 * (including {@code null}).
 * <p>
 * Note: this class implements {@link java.util.List} to provide a standardised set of methods, but
 * note that the implementation itself does not make use of any java classes ({@link List} is only
 * an interface).
 * <p>
 * @author Paolo Lammens (2475444L)
 * @implNote Because of the specification of {@link #subList(int, int)} and how it is
 *         implemented, the nodes {@code head.prev} and {@code tail.prev} aren't {@code null}, so we
 *         check for either end of the list by checking when we've reached {@link #HEAD_SENTINEL} or
 *         {@link #TAIL_SENTINEL}
 */
public class DoublyLinkedList<E> implements List<E> {

    // ----- static members -----
    private static class Node<E> {
        E value;
        Node<E> prev, next;

        public Node(E value, Node<E> prev, Node<E> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }


    /** Link two nodes together such that {@code node2} follows {@code node1} */
    private static <E> void link(Node<E> node1, Node<E> node2) {
        node1.next = node2;
        node2.prev = node1;
    }


// ---- fields ----
    /**
     * sentinel nodes before the head ({@link #getFirst()}) and after the tail ({@link #getLast()})
     * respectively (useful for sub-lists)
     * @see #subList(int, int)
     */
    @NotNull
    private final Node<E> HEAD_SENTINEL, TAIL_SENTINEL;
    /** cached size of list */
    private int size = 0;


// ----- constructors -----

    /** Construct an empty list */
    public DoublyLinkedList() {
        HEAD_SENTINEL = new Node<>(null, null, null);
        TAIL_SENTINEL = new Node<>(null, null, null);
        link(HEAD_SENTINEL, TAIL_SENTINEL);
    }

    /**
     * Construct a linked list as a sub-range of another linked list.
     * <p>
     * As a precondition, the specified list must be well-formed; that is, {@code tail} should be
     * reachable from {@code head} and vice-versa. For internal use only.
     * @param headSentinel head sentinel of the new list (node before head)
     * @param tailSentinel tail sentinel of the new list (node after tail)
     */
    private DoublyLinkedList(@NotNull Node<E> headSentinel, @NotNull Node<E> tailSentinel) {
        HEAD_SENTINEL = headSentinel;
        TAIL_SENTINEL = tailSentinel;
        // count elements to store size cache:
        if (!isEmpty())
            for (Node<E> node = getHead(); node != TAIL_SENTINEL; node = node.next) ++size;
    }


// ----- properties -----

    /**
     * @return first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    public E getFirst() {
        if (isEmpty()) throw new NoSuchElementException("empty list");
        return getHead().value;
    }

    /**
     * @return last element in the list
     * @throws NoSuchElementException if the list is empty
     */
    public E getLast() {
        if (isEmpty()) throw new NoSuchElementException("empty list");
        return getTail().value;
    }

    /**
     * precondition: list must be non-empty
     * @return head node (first node in the list)
     */
    private Node<E> getHead() {
        return HEAD_SENTINEL.next;
    }

    /**
     * precondition: list must be non-empty
     * @return tail node (last node in the list)
     */
    private Node<E> getTail() {
        return TAIL_SENTINEL.prev;
    }

    // ----- methods -----
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return HEAD_SENTINEL.next == TAIL_SENTINEL;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    @NotNull
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }

    @Override
    @NotNull
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int i = 0;
        for (E elem : this) arr[i++] = elem;
        return arr;
    }

    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);

        Object[] typeErasedArr = a;
        int i = 0;
        for (E elem : this) typeErasedArr[i++] = elem;
        if (i < a.length) a[i] = null;

        return a;
    }

    @Override
    public boolean add(E value) {
        insert(TAIL_SENTINEL, value);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (Iterator<E> iter = iterator(); iter.hasNext(); )
            if (Objects.equals(iter.next(), o)) {
                iter.remove();
                return true;
            }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.parallelStream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E elem : c) this.add(elem);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Node<E> position = getNode(index);
        for (E elem : c) insert(position, elem);
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return removeIf((elem) -> guid2475444L.ads2.util.Utils.contains(c, elem));
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return removeIf((elem) -> !Utils.contains(c, elem));
    }

    @Override
    public void clear() {
        link(HEAD_SENTINEL, TAIL_SENTINEL);
        size = 0;
    }

    @Override
    public E get(int index) {
        return getNode(index).value;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = getNode(index);
        E previous = node.value;
        node.value = element;
        return previous;
    }

    @Override
    public void add(int index, E element) {
        insert(getNode(index), element);
    }

    @Override
    public E remove(int index) {
        Node<E> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public int indexOf(Object o) {
        for (ListIterator<E> iter = listIterator(); iter.hasNext(); )
            if (Objects.equals(iter.next(), o)) return iter.previousIndex();
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (ListIterator<E> iter = listIterator(size); iter.hasPrevious(); )
            if (Objects.equals(iter.previous(), o)) return iter.nextIndex();
        return -1;
    }

    @Override
    @NotNull
    public ListIterator<E> listIterator() {
        return new IteratorImpl();
    }

    @Override
    @NotNull
    public ListIterator<E> listIterator(int index) {
        return new IteratorImpl(index);
    }

    @Override
    @NotNull
    public List<E> subList(int fromIndex, int toIndex) {
        checkRangeWithinBounds(fromIndex, toIndex);
        if (isEmpty()) return new DoublyLinkedList<>(HEAD_SENTINEL, TAIL_SENTINEL);
        return new DoublyLinkedList<>(getNode(fromIndex).prev,
                                      toIndex < size ? getNode(toIndex) : TAIL_SENTINEL);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof List)) return false;
        List<?> that = (List<?>) o;
        if (size != that.size()) return false;
        for (Iterator<?> it1 = this.iterator(), it2 = that.iterator(); it1.hasNext(); )
            if (!Objects.equals(it1.next(), it2.next())) return false;
        return true;
    }

    @Override
    public String toString() {
        return "< "
                + String.join(" ⇄ ", (Iterable<String>) stream().map(String::valueOf)::iterator)
                + " >";
    }

    /**
     * Get the node at index index
     * @param index index of node to get
     * @return the node at index {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds
     */
    private Node<E> getNode(int index) {
        checkIndexWithinBounds(index);
        // choose whichever end is closest to start counting from:
        if (index < size / 2) {
            Node<E> node = getHead();
            for (int j = 0; j < index; ++j) node = node.next;
            return node;
        }
        else {
            Node<E> node = getTail();
            for (int j = size - 1; j > index; --j) node = node.prev;
            return node;
        }
    }

    /**
     * Insert a new node at a position specified by its current occupier, pushing the latter and all
     * following nodes forward by one.
     * <p>
     * Precondition: {@code position.prev != null} (i.e. {@code position != HEAD_SENTINEL})
     * @param position node at whose position the new node should be inserted
     * @param value    value of the new element
     */
    private void insert(Node<E> position, E value) {
        Node<E> newNode = new Node<>(value, position.prev, position);
        position.prev.next = newNode;
        position.prev = newNode;
        ++size;
    }

    /**
     * Unlink (remove) a node from the linked list.
     * @param node node in the list (i.e. not a sentinel) to unlink
     */
    private void unlink(Node<E> node) {
        link(node.prev, node.next);
        --size;
    }

    private void checkIndexWithinBounds(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(index + " for list of size " + size);
    }

    /**
     * Check that a pair of indices specify a valid sub-range
     * @param fromIndex start of range (inclusive)
     * @param toIndex   end of range (exclusive)
     * @throws IndexOutOfBoundsException if the specified  range is out of bounds
     */
    private void checkRangeWithinBounds(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException(
                    format("range [%d, %d) out of bounds for list of size %d",
                           fromIndex, toIndex, size));
    }


    /** Iterator class for a DoublyLinkedList */
    private class IteratorImpl implements ListIterator<E> {
        /** index immediately after the current cursor position */
        private int index;
        /** node immediately after the current cursor position */
        private Node<E> node;
        /** node whose value was last returned by {@link #next()} or {@link #previous()} */
        private Node<E> lastAccessed;


        /**
         * Create an iterator initialising its cursor to the position indicated by an index
         * @param startIndex index to which the iterator is to be initialized
         */
        public IteratorImpl(int startIndex) {
            node = (startIndex != size) ? getNode(startIndex) : TAIL_SENTINEL;
            index = startIndex;
        }

        /**
         * Initialize an iterator at one end of the list
         * @param reverse whether to start at the tail instead of the head
         */
        public IteratorImpl(boolean reverse) {
            this(reverse ? size : 0);
        }

        /** Initialize an iterator pointing to the start (head) of the list */
        public IteratorImpl() {
            this(false);
        }


        @Override
        public boolean hasNext() {
            return node != TAIL_SENTINEL;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException("reached end of list");
            lastAccessed = node;
            moveForward();
            return lastAccessed.value;
        }

        @Override
        public boolean hasPrevious() {
            return node.prev != HEAD_SENTINEL;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException("reached start of list");
            lastAccessed = node.prev;
            moveBack();
            return lastAccessed.value;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            checkNextOrPreviousHasBeenCalled();
            unlink(lastAccessed);
            lastAccessed = null;
        }

        @Override
        public void set(E e) {
            checkNextOrPreviousHasBeenCalled();
            lastAccessed.value = e;
        }

        @Override
        public void add(E e) {
            insert(node, e);
        }

        /** Advance the cursor position by one */
        private void moveForward() {
            node = node.next;
            ++index;
        }

        /** Move back the cursor position by one */
        private void moveBack() {
            node = node.prev;
            --index;
        }

        /** check that an appropriate call to next() or previous() has been made */
        private void checkNextOrPreviousHasBeenCalled() {
            if (lastAccessed == null)
                throw new IllegalStateException(
                        "the last element accessed through next() or previous() is unavailable"
                );
        }
    }

}
