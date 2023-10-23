import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class AVLTreeTest {
    private static final Integer[] testSequence1 = { 3, 2, 1, 4 };
    private static final Integer[] testSequence2 = { 3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9 };
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    public String lastPrintedLine() {
        return outputStreamCaptor.toString().trim();
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testInorder() {
        AVLTree<Integer> tree = new AVLTree<>(testSequence1);
        tree.inorder();
        assertEquals("1 2 3 4", lastPrintedLine());
    }

    @Test
    public void testPreorder() {
        AVLTree<Integer> tree = new AVLTree<>(testSequence1);
        tree.preorder();
        assertEquals("2 1 3 4", lastPrintedLine());
    }

    @Test
    public void testPostorder() {
        AVLTree<Integer> tree = new AVLTree<>(testSequence1);
        tree.postorder();
        assertEquals("1 4 3 2", lastPrintedLine());
    }

    @Test
    public void testHeight() {
        assertEquals(2, (new AVLTree<>(testSequence1)).height());
    }

    @Test
    public void testMax() {
        AVLTree<Integer> tree = new AVLTree<>(testSequence1);
        System.out.println(tree.getRoot().getRight().getRight());
        assertEquals(4, (tree).maximum());
    }

    @Test
    public void testMin() {
        assertEquals(1, new AVLTree<>(testSequence1).minimum());
    }

    @Test
    public void testDelete() {
        AVLTree<Integer> tree = new AVLTree<>(testSequence1);
        tree.remove(8);
        tree.inorder();
        assertEquals(4, tree.height());
        assertEquals("1 2 3 4 5 6 7 9 10 11 12 13 14 15 16", lastPrintedLine());
    }

    @Test
    public void testInsert() {
        AVLTree<Integer> tree = new AVLTree<>();

        tree.insert(1);
        assertEquals(1, tree.getRoot().getKey());
        assertEquals(0, tree.getRoot().balance());
        assertEquals(0, tree.getRoot().getHeight());

        tree.insert(2);
        assertEquals(2, tree.getRoot().getRight().getKey());
        assertEquals(1, tree.height());
        assertEquals(1, tree.getRoot().balance());

        tree.insert(3);
        assertEquals(1, tree.height());
        assertEquals(1, tree.getRoot().getLeft().getKey());
        assertNull(tree.getRoot().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(2, tree.getRoot().getKey());
        assertEquals(3, tree.getRoot().getRight().getKey());
        assertNull(tree.getRoot().getRight().getRight());
        assertNull(tree.getRoot().getRight().getLeft());

        tree.insert(4);
        assertEquals(2, tree.height());
        assertEquals(4, tree.getRoot().getRight().getRight().getKey());

        tree.insert(0);
        assertEquals(2, tree.height());
        assertEquals(1, tree.getRoot().getRight().getHeight());
        assertEquals(1, tree.getRoot().getLeft().getHeight());
        assertEquals(0, tree.getRoot().getRight().getRight().getHeight());
        assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        assertEquals(0, tree.getRoot().getLeft().getLeft().getKey());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals(1, tree.getRoot().getLeft().getKey());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(2, tree.getRoot().getKey());
        assertEquals(3, tree.getRoot().getRight().getKey());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals(4, tree.getRoot().getRight().getRight().getKey());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());

        tree.insert(5);
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertEquals(0, tree.getRoot().getLeft().getLeft().getKey());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals(1, tree.getRoot().getLeft().getKey());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(2, tree.getRoot().getKey());
        assertEquals(4, tree.getRoot().getRight().getKey());
        assertEquals(3, tree.getRoot().getRight().getLeft().getKey());
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertEquals(5, tree.getRoot().getRight().getRight().getKey());
        assertNull(tree.getRoot().getRight().getRight().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());

        tree.insert(-1);
        assertEquals(2, tree.height());
        assertEquals(-1, tree.getRoot().getLeft().getLeft().getKey());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals(0, tree.getRoot().getLeft().getKey());
        assertEquals(1, tree.getRoot().getLeft().getRight().getKey());
        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertEquals(2, tree.getRoot().getKey());
        assertEquals(3, tree.getRoot().getRight().getLeft().getKey());
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertEquals(4, tree.getRoot().getRight().getKey());
        assertEquals(1, tree.getRoot().getRight().getHeight());
    }
}