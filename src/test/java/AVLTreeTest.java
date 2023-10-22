import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class AVLTreeTest {
    private static final int[] arr = { 3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9 };
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    public AVLTree<Integer> buildTree() {
        AVLTree<Integer> tree = new AVLTree<Integer>();

        for (int j : arr) {
            tree.insert(j);
        }

        return tree;
    }

    public String lastPrintedLine() {
        return outputStreamCaptor.toString().trim();
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testInorder() {
        AVLTree<Integer> tree = buildTree();
        tree.inOrder();
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16", lastPrintedLine());
    }

    @Test
    public void testPreorder() {
        AVLTree<Integer> tree = buildTree();
        tree.inOrder();
        assertEquals("", lastPrintedLine());
    }

    @Test
    public void testPostorder() {
        AVLTree<Integer> tree = buildTree();
        tree.postOrder();
        assertEquals("", lastPrintedLine());
    }

    @Test
    public void testHeight() {
        assertEquals(16, buildTree().height());
    }

    @Test
    public void testMax() {
        assertEquals(16, buildTree().maximum());
    }

    @Test
    public void testMin() {
        assertEquals(1, buildTree().minimum());
    }

    @Test
    public void testDelete() {
        AVLTree<Integer> tree = buildTree();
        tree.remove(8);
        tree.inOrder();
        assertEquals(4, tree.height());
        assertEquals("1 2 3 4 5 6 7 9 10 11 12 13 14 15 16", lastPrintedLine());
    }

    @Test
    public void testInsert() {
        AVLTree<Integer> tree = buildTree();
        tree.remove(8);
        tree.insert(18);
        tree.inOrder();
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 18", lastPrintedLine());
    }
}