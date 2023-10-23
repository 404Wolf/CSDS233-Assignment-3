public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> root;

    public AVLTree() {}

    public AVLTree(Iterable<T> sequence) {
        for (T element : sequence)
            insert(element);
    }

    public AVLTree(Object[] sequence) {
        for (Object element : sequence)
            insert((T) element);
    }

    /**
     * Empty out the AVL tree.
     *
     * @implSpec Clears the root.
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
        return search(getRoot(), key);
    }

    private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.getKey());
        if (cmp < 0)
            return search(x.getLeft(), key);
        else if (cmp > 0)
            return search(x.getRight(), key);
        else
            return x;
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
     * Delete a node with a specified key.
     ***/
    public void remove(T key) {
        remove(getRoot(), search(key));
    }

    private void remove(AVLTreeNode<T> tree, AVLTreeNode<T> z) {
        // if the root is empty or there are no nodes to delete, return "null"
        if (tree == null || z == null)
            return;
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
