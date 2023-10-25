
public class AVLTreeVisualizer {
    private static final int[] arr = { 3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9 };

    public static void main(String[] args) {
        int i;
        int j;
        AVLTree<Integer> tree = new AVLTree<>();

        System.out.print("== Add in sequence: ");
        for (i = 0; i < arr.length; i++) {
            System.out.printf("%d ", arr[i]);
            tree.insert(arr[i]);
        }

        System.out.print("\n== PreOrder Traversal: ");
        tree.preorder();

        System.out.print("\n== InOrder Traversal: ");
        tree.inorder();

        System.out.print("\n== PostOrder Traversal: ");
        tree.postorder();
        System.out.println();

        System.out.printf("== height: %d\n", tree.height());
        System.out.printf("== Min: %d\n", tree.minimum());
        System.out.printf("== Max: %d\n", tree.maximum());
        System.out.print("== Details of the tree: \n");
        tree.print();

        i = 8;
        System.out.printf("\n== Delete the root node: %d", i);
        tree.remove(i);

        System.out.printf("\n== height: %d", tree.height());
        System.out.print("\n== InOrder Traversal: ");
        tree.inorder();
        System.out.print("\n== Details of the tree: \n");
        tree.print();

        j = 18;
        System.out.printf("\n== Insert the element: %d", j);
        tree.insert(j);

        System.out.print("\n== PreOrder Traversal: ");
        tree.preorder();
    }
}
