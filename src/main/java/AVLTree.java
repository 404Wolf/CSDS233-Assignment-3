import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> root;
    private final Set<T> elements = new HashSet<>();


    public AVLTree() {
    }

    public AVLTree(Iterable<T> sequence) {
        extend(sequence);
    }

    public AVLTree(Object[] sequence) {
        extend(sequence);
    }

    /**
     * Empty out the AVL tree.
     *
     * @implSpec Clears the root.
     */
    public void clear() {
        setRoot(null);
        elements.clear();
    }

    /**
     * Add all the values of a list.
     */
    public void extend(Object[] arr) {
        for (Object element : arr)
            insert((T) element);
    }

    public void extend(Iterable<T> list) {
        for (T element : list)
            insert(element);
    }

    /**
     * Gets the height of the tree.
     *
     * @return The height of the tree.
     */
    public int height() {
        return getRoot().getHeight();
    }

    /**
     * Perform a preorder traversal of the tree, progressively printing out the node keys.
     */
    public void preorder() {
        preorder(getRoot());
    }

    private void preorder(AVLTreeNode<T> tree) {
        if (tree != null) {
            System.out.print(tree.getKey() + " ");
            if (tree.hasLeft()) {
                preorder(tree.getLeft());
            }
            if (tree.hasRight()) {
                preorder(tree.getRight());
            }
        }
    }

    /**
     * Perform an inorder traversal of the tree, progressively printing out the node keys.
     */
    public void inorder() {
        inorder(getRoot());
    }

    private void inorder(AVLTreeNode<T> tree) {
        if (tree != null) {
            if (tree.hasLeft()) {
                inorder(tree.getLeft());
            }
            System.out.print(tree.getKey() + " ");
            if (tree.hasRight()) {
                inorder(tree.getRight());
            }
        }
    }

    /**
     * Perform a postorder traversal of the tree, progressively printing out the node keys.
     */
    private void postorder(AVLTreeNode<T> tree) {
        if (tree != null) {
            if (tree.hasLeft()) {
                postorder(tree.getLeft());
            }
            if (tree.hasRight()) {
                postorder(tree.getRight());
            }
            System.out.print(tree.getKey() + " ");
        }
    }

    public void postorder() {
        postorder(getRoot());
    }

    /**
     * Search tree for node with node specific key.
     *
     * @return The node with the specific key requested.
     */
    public AVLTreeNode<T> search(T key) {
        if (getRoot() == null)
            throw new NoSuchElementException("Empty tree does not have any elements.");

        AVLTreeNode<T> cursor = getRoot();

        int cmp;
        while (cursor != null) {
            cmp = key.compareTo(cursor.getKey());
            if (cmp == 0)
                return cursor;
            else if (cmp < 0)
                cursor = cursor.getLeft();
            else
                cursor = cursor.getRight();
        }
        throw new NoSuchElementException("Element not found.");
    }

    /**
     * Find the inorder successor of a node.
     */
    public AVLTreeNode<T> inorderSuccessor(AVLTreeNode<T> node) {
        AVLTreeNode<T> cursor = node.getRight();
        if (cursor == null)
            return node.getParent();

        while (true) {
            if (cursor.hasLeft())
                cursor = cursor.getLeft();
            else return cursor;
        }
    }

    /**
     * Find the lowest key in the tree.
     *
     * @return The node with the lowest key.
     */
    public T minimum() {
        AVLTreeNode<T> p = minimum(getRoot());
        return p.getKey();
    }

    private AVLTreeNode<T> minimum(AVLTreeNode<T> tree) {
        if (tree.hasLeft())
            return minimum(tree.getLeft());
        else
            return tree;
    }

    /**
     * Find the greatest key in the tree.
     *
     * @return The node with the greatest key.
     */
    public T maximum() {
        return maximum(getRoot()).getKey();
    }

    private AVLTreeNode<T> maximum(AVLTreeNode<T> root) {
        if (root.hasRight())
            return maximum(root.getRight());
        else
            return root;
    }

    /**
     * Swap two nodes in the tree.
     *
     * @param key1 The key of the first node being swapped.
     * @param key2 The key of the second node being swapped.
     */
    public void swap (T key1, T key2) {
        swap(search(key1), search(key2));
    }

    private void swap (AVLTreeNode<T> node1, AVLTreeNode<T> node2) {
        T node1Key = node1.getKey();
        node1.setKey(node2.getKey());
        node2.setKey(node1Key);
    }

    /**
     * Delete a node with a specified key.
     *
     * @param key The key of the node to remove.
     ***/
    public void remove(T key) {
        if (!elements.contains(key))
            throw new NoSuchElementException("Cannot remove an element that doesn't exist in the tree.");
        remove(search(key));
        elements.remove(key);
    }

    private void remove(AVLTreeNode<T> node) {
        // Case 1: Node is a leaf
        // Resolution: Remove the node
        if (node.isLeaf()) {
            if (node.getParent().getLeft() == node)
                node.getParent().setLeft(null);
            else if (node.getParent().getRight() == node)
                node.getParent().setRight(null);
            rebalance(node);
        }
        // Case 2: Node has one child
        // Resolution: Swap the node with its child, then remove the node
        else if (node.hasLeft() ^ node.hasRight()) {
            if (node.hasLeft()) {
                swap(node, node.getLeft());
                remove(node.getLeft());
            }
            else if (node.hasRight()) {
                swap(node, node.getRight());
                remove(node.getRight());
            }
        }
        // Case 3: Node has two children
        // Resolution: Swap node with inorder successor, then delete the node
        else if (node.hasLeft() && node.hasRight()) {
            // First swap the node with its inorder successor (but only update node, we don't actually need to do a
            // complete swap for the inorder successor's parent since we're going to delete it right after).
            AVLTreeNode<T> inorderSuccessor = inorderSuccessor(node);
            swap(node, inorderSuccessor);
            remove(inorderSuccessor);
        }
    }

    /**
     * Insert an element into the tree.
     *
     * @param key The key to assign to the node being inserted.
     */
    public void insert(T key) {
        if (elements.contains(key))
            throw new KeyAlreadyExistsException("The element is already in the tree.");
        else
            elements.add(key);
        if (getRoot() == null)
            setRoot(new AVLTreeNode<>(key, null, null));
        else {
            AVLTreeNode<T> newNode = new AVLTreeNode<>(key, null, null);
            insert(getRoot(), newNode);
            rebalance(newNode);
        }
    }

    private void insert(AVLTreeNode<T> root, AVLTreeNode<T> node) {
        int cmp = node.getKey().compareTo(root.getKey());

        // The key should be inserted into the "left subtree of the tree"
        if (cmp < 0) {
            if (!root.hasLeft())
                root.setLeft(node);
            else
                insert(root.getLeft(), node);
        // The key should be inserted into the "right subtree of the tree"
        } else {
            if (!root.hasRight())
                root.setRight(node);
            else
                insert(root.getRight(), node);
        }
    }

    /**
     * Starting at a specific leaf node traverse upward and progressively rebalance the tree.
     *
     * @param node The leaf node to ascend from.
     */
    private void rebalance(AVLTreeNode<T> node) {
        boolean balancing = true;

        while (node != null) {
            node.updateHeight();
            if (node.hasLeft())
                node.getLeft().updateHeight();
            if (node.hasRight())
                node.getRight().updateHeight();

            if (balancing) {
                 //  No rotations needed and investigation can be ended
                 if (!node.isLeaf() && node.balance() == 0) {
                     balancing = false;
                 }

                if (node.balance() >= 2) {
                    // Right child right subtree
                    if (node.hasRight() && node.getRight().balance() >= 1) {
                        if (node == getRoot())
                            setRoot(node.getRight());
                        node.leftRotate();
                        balancing = false;
                    }
                    // Right child left subtree
                    else if (node.hasRight() && node.getRight().balance() <= 0) {
                        if (node == getRoot())
                            setRoot(node.getRight().getLeft());
                        node.getRight().rightRotate();
                        node.leftRotate();
                        balancing = false;
                    }
                    else throw new RuntimeException("A rotation should have been made.");
                }
                else if (node.balance() <= -2) {
                    // Left child left subtree
                    if (node.hasLeft() && node.getLeft().balance() <= -1) {
                        if (node == getRoot())
                            setRoot(node.getLeft());
                        node.rightRotate();
                        balancing = false;
                    }
                    // Left child right subtree
                    else if (node.hasLeft() && node.getLeft().balance() >= 0) {
                        if (node == getRoot())
                            setRoot(node.getLeft().getRight());
                        node.getLeft().leftRotate();
                        node.rightRotate();
                        balancing = false;
                    }
                    else throw new RuntimeException("A rotation should have been made.");
                }
            }

            node.updateHeight();
            node = node.getParent();
        }
    }

    /**
     * Print the tree.
     *
     * @param key: key-value
     * @param direction 0 means the node this the root node. -1 : -1, means the node is the left child of its parent.
     *                  1 : 1, means the node is the right child of its parent.
     */
    private void print(AVLTreeNode<T> tree, T key, int direction) {
        if (tree != null) {
            if (direction == 0)
                System.out.printf("%2d is root\n", tree.getKey(), key);
            else
                System.out.printf("%2d is %2d's %6s child\n", tree.getKey(), key, direction == 1 ? "right" : "left");

            print(tree.getLeft(), tree.getKey(), -1);
            print(tree.getRight(), tree.getKey(), 1);
        }
    }

    /**
     * Print out information about all the nodes on the tree.
     */
    public void print() {
        if (getRoot() != null)
            print(getRoot(), getRoot().getKey(), 0);
    }

    public AVLTreeNode<T> getRoot() {
        return root;
    }

    protected void setRoot(AVLTreeNode<T> root) {
        if (getRoot() != null)
            getRoot().setIsRoot(false);
        this.root = root;
        if (root != null)
            root.setIsRoot(true);
    }

    public static class AVLTreeNode<T extends Comparable<T>> {
        private T key;
        private int height;
        private boolean isRoot;
        private AVLTreeNode<T> left;
        private AVLTreeNode<T> right;
        private AVLTreeNode<T> parent;

        public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            setKey(key);
            setLeft(left);
            setRight(right);
            setHeight(0);
        }

        /**
         * Determine whether there is at least one child node.
         *
         * @return Whether there is at least one child node.
         */
        public boolean hasChild() {
            return (this.getLeft() != null || this.getRight() != null);
        }

        /**
         * Determine whether the node is a leaf.
         *
         * @return Whether the node is a leaf.
         */
        public boolean isLeaf() { return !hasChild(); }

        /**
         * Obtain the balance of the node.
         *
         * @implNote The balance of a node is equal to the height of the right subtree minus the height of the left subtree.
         * @return The balance of the node.
         */
        public int balance() {
            if (!hasChild())
                return 0;
            else if (getLeft() == null)
                return getRight().getHeight() + 1;
            else if (getRight() == null)
                return -(getLeft().getHeight() + 1);
            else
                return getRight().getHeight() - getLeft().getHeight();
        }

        /**
         * Left rotate the node.
         */
        private void leftRotate(AVLTreeNode<T> node, AVLTreeNode<T> parent) {
            if (parent != null) {
                if (parent.getLeft() == node)
                    parent.setLeft(node.getRight());
                else if (parent.getRight() == node)
                    parent.setRight(node.getRight());
            }

            // Store a reference to the original right child node
            AVLTreeNode<T> nodeRight = node.getRight();

            if (nodeRight != null) {
                // Update node
                node.setRight(nodeRight.getLeft());

                // Update node's left child
                nodeRight.setLeft(node);
                nodeRight.setParent(parent);
            }
        }

        private void leftRotate() {
            leftRotate(this, getParent());
        }

        /**
         * Right rotate the node.
         */
        private void rightRotate(AVLTreeNode<T> node, AVLTreeNode<T> parent) {
            if (parent != null) {
                if (parent.getRight() == node)
                    parent.setRight(node.getLeft());
                else if (parent.getLeft() == node)
                    parent.setLeft(node.getLeft());
            }

            // Store a reference to the original left child node
            AVLTreeNode<T> nodeLeft = node.getLeft();

            // Update node
            if (nodeLeft != null) {
                node.setLeft(nodeLeft.getRight());

                // Update node's left child
                nodeLeft.setRight(node);
                nodeLeft.setParent(parent);
            }
        }

        private void rightRotate() {
            rightRotate(this, getParent());
        }

        public void updateHeight() { setHeight(1 + Math.max(height(getLeft()), height(getRight()))); }

        private int height(AVLTreeNode<T> node) { return node == null ? -1 : node.getHeight(); }

        public int getHeight() {
            return height;
        }

        protected void setHeight(int height) {
            this.height = height;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public AVLTreeNode<T> getLeft() {
            return left;
        }

        public boolean hasLeft() {
            return getLeft() != null;
        }

        protected void setLeft(AVLTreeNode<T> left) {
            this.left = left;
            if (left != null)
                left.setParent(this);
        }

        public AVLTreeNode<T> getRight() {
            return right;
        }

        public boolean hasRight() {
            return getRight() != null;
        }

        protected void setRight(AVLTreeNode<T> right) {
            this.right = right;
            if (right != null)
                right.setParent(this);
        }

        @Override
        public String toString() {
            return "AVLNode(key=" + getKey() + ", left=" + (getLeft() == null ? "null" : getLeft().getKey()) + ", right=" + (getRight() == null ? "null" : getRight().getKey()) + ", height=" + getHeight() + ", balance=" + balance() + ")";
        }

        public AVLTreeNode<T> getParent() {
            return parent;
        }

        protected void setParent(AVLTreeNode<T> parent) {
            this.parent = parent;
        }

        public boolean isRoot() {
            return isRoot;
        }

        public void setIsRoot(boolean isRoot) {
            this.isRoot = isRoot;
        }
    }
}
