/*******************************************************
 * Assignemnt 3
 * Name: Wolf Mermelstein
 * UID: wsm32
 ********************************************************/

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> mRoot;

    public AVLTree() {
        mRoot = null;
    }

    /**
     * Gets the height of the tree.
     *
     * @return The height of the tree.
     */
    private int height(AVLTreeNode<T> tree) {
        if (tree != null)
            return tree.height;

        return 0;
    }

    public int height() {
        return height(mRoot);
    }

    /**
     * Question: a-1
     * Preorder traversal "AVL tree", print the result
     */
    private void preOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            /*
             * Write your code here
             * use: System.out.print(tree.key + " ");
             */
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /**
     * Question: a-2
     * In-order traversal "AVL tree", print the result
     */
    private void inOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            /*
             * Write your code here
             * use: System.out.print(tree.key + " ");
             */
        }
    }

    public void inOrder() {
        inOrder(mRoot);
    }

    /**
     * Question: a-3
     * Post-order traversal "AVL tree", print the result
     */
    private void postOrder(AVLTreeNode<T> tree) {
        if (tree != null) {
            /*
             * Write your code here
             * use: System.out.print(tree.key + " ");
             */
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }

    /**
     * (Recursion) Search the node whose key-value is key in "AVL tree x"
     */
    private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
        if (x == null)
            return x;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return search(x.left, key);
        else if (cmp > 0)
            return search(x.right, key);
        else
            return x;
    }

    public AVLTreeNode<T> search(T key) {
        return search(mRoot, key);
    }

    /**
     * (Non-Recursion) Search the node whose key-value is key in "AVL tree x"
     */
    private AVLTreeNode<T> iterativeSearch(AVLTreeNode<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x;
        }

        return x;
    }

    public AVLTreeNode<T> iterativeSearch(T key) {
        return iterativeSearch(mRoot, key);
    }

    /**
     * Question: a-4
     * Find min node：return the smallest node of the AVL tree when "tree" as the
     * root
     */
    private AVLTreeNode<T> minimum(AVLTreeNode<T> tree) {

        /*
         * Write your code here
         */
        return tree;
    }

    public T minimum() {
        AVLTreeNode<T> p = minimum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /**
     * Question: a-5
     * Finds max node: return the largest node of the AVL tree with "tree" as the root
     */
    private AVLTreeNode<T> maximum(AVLTreeNode<T> tree) {
        /*
         * Write your code here
         */
        return tree;
    }

    public T maximum() {
        AVLTreeNode<T> p = maximum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /**
     * Left rotate a tree.
     *
     * @return The root node after rotated
     */
    private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;

        k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;

        return k1;
    }

    /**
     * Right rotate a tree.
     *
     * @return The root node after rotated
     */
    private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1) {
        AVLTreeNode<T> k2;

        k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;

        return k2;
    }

    /**
     * Double left rotate a tree.
     *
     * @return The root node after rotated
     */
    private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
        k3.left = rightRightRotation(k3.left);

        return leftLeftRotation(k3);
    }

    /**
     * Double right rotate a node.
     *
     * @return The root node after rotated
     */
    private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
        k1.right = leftLeftRotation(k1.right);

        return rightRightRotation(k1);
    }

    /**
     * Question a-6
     * Inserts an element into the tree.
     *
     * @param tree The root node of AVL tree.
     * 
     * @param key The insertion key-value.
     * 
     * @return The root node of the tree.
     */
    private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
        if (tree == null) {
            tree = new AVLTreeNode<T>(key, null, null);
            if (tree == null) {
                System.out.println("ERROR: create avltree node failed!");
                return null;
            }
        } else {
            int cmp = key.compareTo(tree.key);

            if (cmp < 0) { // Case: The key should be inserted into the "left subtree of the tree"
                /*
                 * Write your code here
                 */
            } else if (cmp > 0) { // Case: The key should be inserted into the "right subtree of the tree"
                /*
                 * Write your code here
                 * If the AVL tree is out of balance after the node is inserted, adjust it
                 * accordingly.
                 */
            } else { // cmp==0
                System.out.println("Insert Fail：Cannot insert the same element！");
            }
        }

        tree.height = Math.max(height(tree.left), height(tree.right)) + 1;

        return tree;
    }

    public void insert(T key) {
        mRoot = insert(mRoot, key);
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

        int cmp = z.key.compareTo(tree.key);

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
            if ((tree.left != null) && (tree.right != null)) {
                if (height(tree.left) > height(tree.right)) {
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
                tree = (tree.left != null) ? tree.left : tree.right;
                tmp = null;
            }
        }

        return tree;
    }

    public void remove(T key) {
        AVLTreeNode<T> z;

        if ((z = search(mRoot, key)) != null)
            mRoot = remove(mRoot, z);
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
                System.out.printf("%2d is root\n", tree.key, key);
            else
                System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction == 1 ? "right" : "left");

            print(tree.left, tree.key, -1);
            print(tree.right, tree.key, 1);
        }
    }

    public void print() {
        if (mRoot != null)
            print(mRoot, mRoot.key, 0);
    }

    static class AVLTreeNode<T extends Comparable<T>> {
        T key; // key
        int height; // height
        AVLTreeNode<T> left; // left child
        AVLTreeNode<T> right; // right child

        public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }
}
