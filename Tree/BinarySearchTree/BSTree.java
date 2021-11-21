/**
 * BSTree class
 * 
 * void insert(element) -->Insert element
 * void remove(element) -->Remove element
 * Comparable findMin( )  --> Return smallest item
 * Comparable findMax( )  --> Return largest item
 * void printTree( )      --> Print tree in sorted order
 */

/**
 * Implements an unbalanced binary search tree.
 */
public class BSTree {

    /**
     * Insert into the tree; duplicates are ignored.
     * @param element
     */
    public void insert(Integer element) {
        root = insert(element, root);
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void print() {
        print(root);
    }

    /**
     * emove from the tree. Nothing is done if x is not found.
     * @param element
     */
    public void remove(Integer element) {
        root = remove(element, root);
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public Node findmin() {
        return findmin(root);
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public Node findmax() {
        return findmax(root);
    }

    /**
    * Internal method to insert into a subtree.
    * @param element
    * @param root
    * @return the new root of the subtree.
    */
    private Node insert(Integer element, Node root) {

        if (root == null)
            return new Node(element, null, null);
        int result = element.compareTo(root.element);

        if (result < 0)
            root.left = insert(element, root.left);
        else if (result > 0)
            root.right = insert(element, root.right);
        return root;
    }

    /**
     * Internal method to remove from a subtree.
     * @param element the item to remove.
     * @param root the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private Node remove(Integer element, Node root) {
        if (root == null)
            return root;
        int result = element.compareTo(root.element);
        if (result < 0)
            root.left = remove(element, root.left);
        else if (result > 0)
            root.right = remove(element, root.right);
        else {
            if (root.left != null || root.right != null) {
                Node ret = findmin(root.right);
                if (ret == null) {
                    return root.left;
                }
                int x = ret.element;
                root.element = x;
                root.right = remove(x, root.right);
                return root;
            } else
                return null;

        }
        return root;
    }

    /**
     * 
     * @param root
     * @return
     */
    public int height(Node root) {
        if (root == null)
            return -1;
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * 循环实现
     * @param root
     * @return node containing the smallest item.
     */
    private Node findmin(Node root) {
        Node ret = null;
        while (root != null) {
            ret = root;
            root = root.left;
        }
        return ret;
    }

    /**
     * 递归实现
     */
    private Node findminB(Node root) {
        if (root == null || root.left == null)
            return root;
        return findmin(root.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * 循环实现
     * @param root the node that roots the subtree.
     * @return node containing the largest item.
     */
    private Node findmax(Node root) {
        Node ret = null;
        while (root != null) {
            ret = root;
            root = root.left;
        }
        return ret;
    }

    /**
     * 递归实现
     */
    private Node findmaxB(Node root) {
        if (root == null || root.left == null)
            return root;
        return findmaxB(root.right);
    }

    /**
    * Internal method to print a subtree in sorted order.
    * @param root the node that roots the subtree.
    */
    private void print(Node root) {

        if (root == null)
            return;

        print(root.left);
        System.out.println(root.element);
        print(root.right);
    }

    private Node root;

    public static void main(String[] args) {
        BSTree t = new BSTree();
        int NUM = 10;
        int GAP = 3;

        for (int i = GAP; i != 0; i = (i + GAP) % NUM)
            t.insert(i);
        t.print();
        System.out.println("height:" + t.height(t.root));
        System.out.println("min:" + t.findmin(t.root).element);
        System.out.println("minB:" + t.findminB(t.root).element);
        for (int i = 1; i < NUM; i += 2)
            t.remove(i);
        t.print();
        System.out.println("END");
    }
}

class Node {
    Integer element;
    Node left;
    Node right;

    public Node(Integer element, Node left, Node right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }
}