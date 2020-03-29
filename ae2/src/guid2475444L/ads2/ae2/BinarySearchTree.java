package guid2475444L.ads2.ae2;


import java.lang.reflect.Array;
import java.util.*;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Binary search tree. Cannot contain {@code null} elements.
 * <p>
 * Note: this interface extends {@link Collection} just because it defines useful methods and so
 * that implementing objects can be passed to functions with a Collection as parameter, but all the
 * relevant operations have been redefined here in {@link DynamicSet}.
 * @author - Paolo Lammens (2475444L)
 */
public class BinarySearchTree<E extends Comparable<E>> implements DynamicSet<E> {

    private static class Node<E> {
        enum ChildType {LEFT, RIGHT, ORPHAN}

        E value;
        Node<E> parent, left, right;

        Node(E value, Node<E> parent, Node<E> left, Node<E> right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        Node(E value, Node<E> parent) {
            this(value, parent, null, null);
        }

        boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        ChildType getChildType() {
            if (parent == null) return ChildType.ORPHAN;
            assert (this == parent.left || this == parent.right);
            if (this == parent.left) return ChildType.LEFT;
            else return ChildType.RIGHT;
        }

        @Override
        public String toString() {
            return String.format("(%s) - %s - (%s)", left != null ? left.value : null,
                                 value, right != null ? right.value : null);
        }
    }

    private int size = 0;
    private Node<E> root;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        try {
            return find(root, (E) o) != null;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new InorderTraversal(root);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        for (E elem : this) arr[i++] = elem;
        return arr;
    }

    @NotNull
    @Override
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
    public boolean add(E e) {
        if (root == null) {
            root = new Node<>(e, null);
            ++size;
            return true;
        }
        else return insert(root, e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        try {
            Node<E> node = find(root, (E) o);
            if (node == null) return false;
            remove(node);
            return true;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean changed = false;
        for (E x : c) if (add(x)) changed = true;
        return changed;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return removeIf(c::contains);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return removeIf((elem) -> !c.contains(elem));
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    // ---------- DynamicSet-specific methods ----------

    @Override
    public DynamicSet<E> union(@NotNull DynamicSet<? extends E> other) {
        return null;
    }

    @Override
    public DynamicSet<E> intersect(@NotNull DynamicSet<? extends E> other) {
        return null;
    }

    @Override
    public DynamicSet<E> minus(@NotNull DynamicSet<? extends E> other) {
        return null;
    }

    @Override
    public boolean isSubsetOf(@NotNull DynamicSet<?> other) {
        return false;
    }

    // ---------- Helper (private) methods ----------

    /**
     * Insert a new node with a given value
     * @param root  root of the subtree to insert the new node in
     * @param value element value of new node
     * @return whether a new node has been inserted (i.e. whether {@code value} was NOT present in
     *         the subtree before insertion)
     */
    private boolean insert(Node<E> root, E value) {
        int comparison = value.compareTo(root.value);
        if (comparison < 0)
            if (root.left == null) return insertLeftLeaf(root, value);
            else return insert(root.left, value);
        else if (comparison > 0)
            if (root.right == null) return insertRightLeaf(root, value);
            else return insert(root.right, value);
        else return false;
    }

    /** insert a new node as the left child of another (which previously didn't have one) */
    private boolean insertLeftLeaf(Node<E> node, E value) {
        assert node.left == null;
        node.left = new Node<>(value, node);
        ++size;
        return true;
    }

    /** insert a new node as the right child of another (which previously didn't have one) */
    private boolean insertRightLeaf(Node<E> node, E value) {
        assert node.right == null;
        node.right = new Node<>(value, node);
        ++size;
        return true;
    }

    /**
     * Find the {@link Node} matching the given value in a subtree
     * @param root  root of the subtree to search in
     * @param value value to match
     * @return the {@link Node} matching the given value, or {@code null} if not present
     */
    private @Nullable Node<E> find(Node<E> root, E value) {
        if (root == null) return null;
        int comparison = root.value.compareTo(value);
        if (comparison > 0) return find(root.left, value);
        else if (comparison < 0) return find(root.right, value);
        else return root;
    }

    /**
     * Remove a single node while maintaining the BST property
     * @param node node to remove
     */
    private void remove(Node<E> node) {
        if (node.isLeaf()) replace(node, null);
        else if (node.right == null) replace(node, node.left);
        else if (node.left == null) replace(node, node.right);
        else {
            InorderTraversal iter = new InorderTraversal(node.right);
            replace(node, iter.nextNode());
        }
        --size;
    }

    /**
     * Replace a subtree with another
     * <p>
     * Does not update {@link #size} (responsibility of the caller)
     * @param subtree     root node of the subtree to be replaced
     * @param replacement root node of the subtree to use as replacement
     */
    private void replace(@NotNull Node<E> subtree, @Nullable Node<E> replacement) {
        if (replacement != null) unlink(replacement);
        Node.ChildType childType = subtree.getChildType();
        if (childType == Node.ChildType.LEFT) subtree.parent.left = null;
        else if (childType == Node.ChildType.RIGHT) subtree.parent.right = null;
        else root = replacement;
    }

    /**
     * Unlink a subtree from the rest of the tree.
     * <p>
     * Does not update {@link #size} (responsibility of the caller)
     * @param subtree root node of the subtree to be unlinked
     */
    private void unlink(@NotNull Node<E> subtree) {
        Node.ChildType childType = subtree.getChildType();
        if (childType == Node.ChildType.LEFT) subtree.parent.left = null;
        else if (childType == Node.ChildType.RIGHT) subtree.parent.right = null;
        subtree.parent = null;
    }


    /** implements inorder traversal */
    private class InorderTraversal implements Iterator<E> {
        /**
         * Inorder traversal stack.
         * <p>
         * The node at the top of this stack contains the element that will be returned in the next
         * call to {@link #next()}—if {@link #ready}; otherwise it contains the element returned by
         * the most recent call to {@link #next()}.
         * <p>
         * The boolean tag assigned to each node indicates whether that node has already been
         * visited (i.e. its element returned by {{@link #next()}}).
         */
        private final Deque<Pair<Node<E>, Boolean>> stack;
        /** weather {@link #advance()} has been called before the next call to {@link #next()} */
        boolean ready = false;

        InorderTraversal(Node<E> root) {
            // Initialise inorder traversal stack:
            stack = new ArrayDeque<>(size);
            // push dummy node that will redirect to root at first iteration:
            stack.push(MutablePair.of(new Node<>(null, null, null, root), true));
        }

        /** get the node currently pointed at by this iterator */
        private Node<E> getCurrent() {
            return stack.getFirst().getKey();
        }

        @Override
        public boolean hasNext() {
            if (!ready) advance();
            return stack.size() > 1;  // bottom element in stack is dummy node
        }

        public Node<E> nextNode() {
            if (!ready) advance();
            if (!hasNext()) throw new NoSuchElementException("end of iteration");
            ready = false;  // reset for following iteration
            return getCurrent();
        }

        @Override
        public E next() {
            return nextNode().value;
        }

        @Override
        public void remove() {
            if (ready) throw new IllegalStateException();
            Node<E> toRemove = getCurrent();
            ready = (toRemove.right != null);
            BinarySearchTree.this.remove(toRemove);
        }

        /**
         * Find the next node in the iteration.
         * <p>
         * <b>Precondition:</b> {@code !stack.isEmpty()}<br>
         * <b>Postcondition:</b> the element to be returned by the
         * following call to {@link #next()} is at the top of the {@link #stack} (i.e. can be
         * accessed through {@code stack.peek()}) or equivalently {@code stack.getFirst()}
         * @throws NoSuchElementException if there are no remaining elements in the iteration
         */
        private void advance() {
            Pair<Node<E>, Boolean> traversalNode = stack.getFirst();  // peek
            Node<E> node = traversalNode.getKey();
            if (node.right != null) {
                stack.push(MutablePair.of(node.right, false));
                advanceToLeftLeaf();
            }
            else {
                do { stack.pop(); }
                while ((traversalNode = stack.peek()) != null && traversalNode.getValue());
                if (traversalNode != null) traversalNode.setValue(true);
            }
            ready = true;
        }

        /**
         * Advance the traversal {@link #stack} to the leftmost leaf rooted at the current node (the
         * node at the top of the stack)
         * <p>
         * <b>Precondition:</b> {@code !stack.isEmpty()}<br>
         */
        private void advanceToLeftLeaf() {
            for (Node<E> node = getCurrent().left; node != null; node = node.left)
                stack.push(MutablePair.of(node, false));
            stack.getFirst().setValue(true);  // set top of stack to visited
        }
    }

}