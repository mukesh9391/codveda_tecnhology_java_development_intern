import java.util.LinkedList;
import java.util.Queue;

class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode(int value) {
        this.value = value;
        left = right = null;
    }
}

public class BinarySearchTree {
    private TreeNode root;

    // Insert a new node
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root; // unchanged node
    }

    // Search for a node
    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(TreeNode root, int value) {
        if (root == null) return false;
        if (root.value == value) return true;
        return value < root.value ? searchRec(root.left, value) : searchRec(root.right, value);
    }

    // Delete a node
    public void delete(int value) {
        root = deleteRec(root, value);
    }

    private TreeNode deleteRec(TreeNode root, int value) {
        if (root == null) return null;

        if (value < root.value) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.value) {
            root.right = deleteRec(root.right, value);
        } else {
            // Node found
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Node with two children ? get inorder successor (smallest in right subtree)
            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }
        return root;
    }

    private int minValue(TreeNode root) {
        int min = root.value;
        while (root.left != null) {
            min = root.left.value;
            root = root.left;
        }
        return min;
    }

    // In-order traversal (Left ? Root ? Right)
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.value + " ");
            inorderRec(root.right);
        }
    }

    // Pre-order traversal (Root ? Left ? Right)
    public void preorder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(TreeNode root) {
        if (root != null) {
            System.out.print(root.value + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    // Post-order traversal (Left ? Right ? Root)
    public void postorder() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(TreeNode root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.value + " ");
        }
    }

    // ? Level-order traversal (BFS)
    public void levelOrder() {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.value + " ");

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        System.out.println();
    }

    // ? Print tree structure (sideways)
    public void printTree() {
        printTreeRec(root, 0);
    }

    private void printTreeRec(TreeNode node, int space) {
        if (node == null) return;

        // Increase distance between levels
        space += 5;

        // Print right child first
        printTreeRec(node.right, space);

        // Print current node with indentation
        System.out.println();
        for (int i = 5; i < space; i++) {
            System.out.print(" ");
        }
        System.out.println(node.value);

        // Print left child
        printTreeRec(node.left, space);
    }

    // Main method for testing
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // Insert nodes
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        System.out.println("In-order Traversal:");
        bst.inorder();

        System.out.println("Pre-order Traversal:");
        bst.preorder();

        System.out.println("Post-order Traversal:");
        bst.postorder();

        System.out.println("Level-order Traversal (BFS):");
        bst.levelOrder();

        System.out.println("\nVisual Tree:");
        bst.printTree();

        System.out.println("\nDeleting 20...");
        bst.delete(20);
        bst.printTree();

        System.out.println("\nDeleting 30...");
        bst.delete(30);
        bst.printTree();

        System.out.println("\nDeleting 50...");
        bst.delete(50);
        bst.printTree();
    }
}
