import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class AVLTreeTest {
    private static final Integer[] testSequence1 = { 3, 2, 1, 4 };
    private static final Integer[] testSequence2 = { 1, 0, 2, 3, 4, 5, 6, -1, 7 };
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
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(1);
        tree.insert(0);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        assertEquals(0, tree.getRoot().getLeft().getHeight());
        assertEquals(1, tree.getRoot().getRight().getHeight());
        assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        tree.insert(5);
        tree.insert(6);
        assertEquals(1, tree.getRoot().getLeft().getHeight());
        assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        assertEquals(1, tree.getRoot().getRight().getHeight());
        assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        tree.insert(7);
        assertEquals(1, tree.getRoot().getLeft().getHeight());
        assertEquals(2, tree.getRoot().getRight().getHeight());

        tree.clear();
        tree.insert(3);
        assertEquals(0, tree.height());
        tree.insert(2);
        assertEquals(1, tree.height());
        tree.insert(1);
        assertEquals(1, tree.height());
        tree.insert(4);
        assertEquals(1, tree.getRoot().getRight().getHeight());
        assertEquals(2, tree.height());

        assertEquals(3, (new AVLTree<>(testSequence2)).height());
    }

    @Test
    public void testMax() {
        assertEquals(4, (Integer) (new AVLTree<>(testSequence1)).maximum());
        assertEquals(7, (Integer)(new AVLTree<>(testSequence2)).maximum());
    }

    @Test
    public void testMin() {
        assertEquals(1, (Integer)(new AVLTree<>(testSequence1)).minimum());
        assertEquals(-1, (Integer)(new AVLTree<>(testSequence2)).minimum());
    }

    @Test
    public void testInorderSuccessor() {
        AVLTree<Integer> tree = new AVLTree<>(testSequence1);
        assertEquals(3, tree.inorderSuccessor(tree.getRoot()).getKey());
        assertEquals(2, tree.inorderSuccessor(tree.getRoot().getLeft()).getKey());
        tree.insert(0);
        tree.insert(5);
        assertEquals(4, tree.inorderSuccessor(tree.getRoot().getRight().getLeft()).getKey());
        assertEquals(5, tree.inorderSuccessor(tree.getRoot().getRight()).getKey());
        assertEquals(1, tree.inorderSuccessor(tree.getRoot().getLeft().getLeft()).getKey());
    }

    @Test
    public void testDelete() {
        AVLTree<Integer> tree = new AVLTree<>();

        // Test a case 1 deletion (node is a leaf)
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.remove(3);
        assertNull(tree.getRoot().getRight());
        assertEquals(1, tree.getRoot().getLeft().getKey());
        assertEquals(2, tree.getRoot().getKey());

        tree.clear();
        // Test a case 2 deletion (node has one child)
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.remove(3);
        assertEquals(4, tree.getRoot().getRight().getKey());

        tree.clear();
        // Test a case 3 deletion (node has two children)
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(5);
        tree.insert(3);
        tree.remove(4);
        assertEquals(2, tree.getRoot().getKey());
        assertEquals(5, tree.getRoot().getRight().getKey());
        assertNull(tree.getRoot().getRight().getRight());

        // Test an additional case 3 deletion involving rebalancing
        tree = new AVLTree<>(testSequence2);

        tree.remove(3);
        assertEquals(tree.getRoot(), tree.getRoot().getRight().getParent());
        assertEquals(3, tree.height());
        assertEquals(1, tree.getRoot().getRight().getHeight());
        assertEquals(4, tree.getRoot().getKey());
        assertEquals(1, tree.getRoot().getLeft().getKey());
        assertEquals(0, tree.getRoot().getLeft().getLeft().getKey());
        assertEquals(5, tree.getRoot().getRight().getLeft().getKey());
        assertEquals(6, tree.getRoot().getRight().getKey());
        assertEquals(7, tree.getRoot().getRight().getRight().getKey());
        assertNull(tree.getRoot().getRight().getRight().getRight());
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