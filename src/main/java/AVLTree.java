import java.util.Arrays;
import java.util.LinkedList;

/*******************************************************
 * Assignemnt 3
 * Name: Wolf Mermelstein
 * UID: wsm32
 ********************************************************/

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> root;

    public AVLTree() {
        setRoot(null);
    }

    /**
     * Empty out the AVL tree.
     */
    public void clear() {
        setRoot(null);
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
     * Question: a-1
     * Preorder traversal "AVL tree", print the result
     */
    private void preOrder(AVLTreeNode<T> tree) {
    }

    public void preOrder() {
        preOrder(getRoot());
    }

    /**
     * Question: a-2
     * In-order traversal "AVL tree", print the result
     */
    private void inOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            if (tree.getLeft() != null) {
                inOrder(tree.getLeft());
                System.out.print(tree.getLeft() + " ");
            }
            System.out.print(tree);
            if (tree.getRight() != null) {
                inOrder(tree.getRight());
                System.out.print(tree.getRight() + " ");
            }
        }
    }

    public void inOrder() {
        inOrder(getRoot());
    }

    /**
     * Question: a-3
     * Post-order traversal "AVL tree", print the result
     */
    private void postOrder(AVLTreeNode<T> tree) {
    }

    public void postOrder() {
        postOrder(getRoot());
    }

    /**
     * Search tree for node with node specific key.
     */
    private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
        if (x == null)
            return x;

        int cmp = key.compareTo(x.getKey());
        if (cmp < 0)
            return search(x.getLeft(), key);
        else if (cmp > 0)
            return search(x.getRight(), key);
        else
            return x;
    }

    public AVLTreeNode<T> search(T key) {
        return search(getRoot(), key);
    }

    /**
     * (Non-Recursion) Search the node whose key-value is key in "AVL tree x"
     */
    private AVLTreeNode<T> iterativeSearch(AVLTreeNode<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.getKey());

            if (cmp < 0)
                x = x.getLeft();
            else if (cmp > 0)
                x = x.getRight();
            else
                return x;
        }

        return x;
    }

    public AVLTreeNode<T> iterativeSearch(T key) {
        return iterativeSearch(getRoot(), key);
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
        if (tree.getLeft() != null)
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
        AVLTreeNode<T> p = maximum(getRoot());
        if (p != null)
            return p.getKey();

        return null;
    }

    private AVLTreeNode<T> maximum(AVLTreeNode<T> tree) {
        if (tree.getRight() != null)
            return minimum(tree.getRight());
        else
            return tree;
    }

    /**
     * Insert an element into the tree.
     *
     * @param key The key to insert.
     */
    public void insert(T key) {
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

    private void rebalance(AVLTreeNode<T> node) {
        AVLTreeNode<T> prev = null;
        AVLTreeNode<T> prePrev = null;
        boolean stillRequiresBalance = true;

        while (node != null) {
            node.updateHeight();

            if (stillRequiresBalance) {
                // If the balance is 0 we can stop investigating
                if (node.hasChild() && node.balance() == 0)
                    stillRequiresBalance = false;

                // If the balance is -2 or 2 then we do need to rotate, depending on
                if (Math.abs(node.balance()) == 2) {
                    // Right child right subtree -> rotate node left
                    if (node.hasRight() && prePrev == node.getRight().getRight()) {
                        if (getRoot() == node)
                            setRoot(node.getRight());
                        node.leftRotate();
                    }
                    // Left child left subtree -> rotate node right
                    if (node.hasLeft() && prePrev == node.getLeft().getLeft()) {
                        if (getRoot() == node)
                            setRoot(node.getLeft());
                        node.rightRotate();
                    }
                    if (prev != null) {
                        // Left child right subtree -> rotate prev left, rotate node right
                        if (node.hasLeft() && prePrev == node.getLeft().getRight()) {
                            prev.leftRotate();
                            node.rightRotate();
                        }
                        // Right child right subtree -> rotate prev right, rotate node left
                        if (node.hasRight() && prePrev == node.getRight().getLeft()) {
                            prev.rightRotate();
                            node.leftRotate();
                        }
                    }

                    // Since a rotation has been preformed the investigation is complete.
                    stillRequiresBalance = false;
                }
            }

            node.updateHeight();

            prePrev = prev;
            prev = node;
            node = node.getParent();
        }
    }

    /**
     * Question: a-7
     * Delete the node (z), then return the root node
     *
     * @param tree: the root node of AVL tree
     * 
     * @param z: the node to be deleted
     * 
     * @return tree: root node
     */
    private AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> z) {
        // if the root is empty or there are no nodes to delete, return "null"
        if (tree == null || z == null)
            return null;

        int cmp = z.getKey().compareTo(tree.getKey());

        if (cmp < 0) { // The node to be deleted is in the "left subtree of tree"

            /*
             * Write your code here
             * If the AVL tree is out of balance after the node is deleted, adjust it
             * accordingly.
             */

        } else if (cmp > 0) { // The node to be deleted is in the "right subtree of tree"

            /*
             * Write your code here
             * If the AVL tree is out of balance after the node is deleted, adjust it
             * accordingly.
             */

        } else {
            // If both the left and right children of "tree" are not empty
            if ((tree.getLeft() != null) && (tree.getRight() != null)) {
                if (tree.getLeft().getHeight() > tree.getRight().getHeight()) {
                    /*
                     * Write your code here
                     */

                } else {
                    /*
                     * Write your code here
                     */
                }
            } else {
                AVLTreeNode<T> tmp = tree;
                tree = (tree.getLeft() != null) ? tree.getLeft() : tree.getRight();
                tmp = null;
            }
        }

        return tree;
    }

    public void remove(T key) {
        AVLTreeNode<T> z;

        if ((z = search(getRoot(), key)) != null)
            setRoot(remove(getRoot(), z));
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

    public void print() {
        if (getRoot() != null)
            print(getRoot(), getRoot().getKey(), 0);
    }

    public AVLTreeNode<T> getRoot() {
        return root;
    }

    protected void setRoot(AVLTreeNode<T> mRoot) {
        this.root = mRoot;
    }

    public static class AVLTreeNode<T extends Comparable<T>> {
        private T key;
        private int height;
        private int balance;
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
        protected void leftRotate(AVLTreeNode<T> node, AVLTreeNode<T> parent) {
            if (parent != null) {
                if (parent.getLeft() == node)
                    parent.setLeft(node.getRight());
                else if (parent.getRight() == node)
                    parent.setRight(node.getRight());
            }

            // Store a reference to the original right child node
            AVLTreeNode<T> nodeRight = node.getRight();

            // Update node
            node.setRight(nodeRight.getLeft());

            // Update node's left child
            nodeRight.setLeft(node);
            nodeRight.setParent(parent);
        }

        protected void leftRotate() {
            leftRotate(this, getParent());
        }

        /**
         * Right rotate the node.
         */
        protected void rightRotate(AVLTreeNode<T> node, AVLTreeNode<T> parent) {
            if (parent != null) {
                if (parent.getRight() == node)
                    parent.setRight(node.getLeft());
                else if (parent.getLeft() == node)
                    parent.setLeft(node.getLeft());
            }

            // Store a reference to the original left child node
            AVLTreeNode<T> nodeLeft = node.getLeft();

            // Update node
            node.setLeft(nodeLeft.getRight());

            // Update node's left child
            nodeLeft.setRight(node);
            nodeLeft.setParent(parent);
        }

        protected void rightRotate() {
            rightRotate(this, getParent());
        }

        public void updateHeight() {
            setHeight(1 + Math.max(height(getLeft()), height(getRight())));
        }

        private int height(AVLTreeNode<T> node) {
            return node == null ? -1 : node.getHeight();
        }

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
    }
}
