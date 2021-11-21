public class BSTreeB {

    public void insert(int element) {
        root = insert(element, root);
    }

    private Nodea insert(int element, Nodea root) {
        if (root == null) {
            Nodea tmp = new Nodea(element, null, null, null);
            root = tmp;
            return root;
        } else if (element <= root.element) {
            root.left = insert(element, root.left);
            root.left.parent = root;
        } else if (element > root.element) {
            root.right = insert(element, root.right);
            root.right.parent = root;
        }
        return root;
    }

    public void print(Nodea root) {
        if (root == null)
            return;
        print(root.left);
        System.out.printf("%d\t", root.element);
        print(root.right);
    }

    public Nodea search(int des) {
        return search(des, root);
    }

    private Nodea search(int des, Nodea root) {
        if (root == null || root.element == des)
            return root;
        else if (des < root.element)
            return search(des, root.left);
        else
            return search(des, root.right);
    }

    private Nodea findmin(Nodea root) {
        if (root != null && root.left != null)
            return findmin(root.left);
        return root;
    }

    public int delete(int des) {
        Nodea p = null;
        Nodea del = null;
        int result = 0;
        if ((p = search(des)) != null) {
            result = p.element;
            if ((del = delete(p, root)) != null)
                ;
        }
        return result;
    }

    private Nodea delete(Nodea p, Nodea root) {
        Nodea y = null;
        Nodea x = null;

        if ((p.left == null) || (p.right == null)) {
            y = p;
            System.out.printf("可以直接删除 %d\n", y.element);
        } else {
            y = findmin(p);
            System.out.printf("代替 %d 的将会是 %d\n", p.element, y.element);
        }

        if (y.left != null)
            x = y.left;
        else
            x = y.right;

        if (y.parent == null)
            root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;

        if (y != p)
            p.element = y.element;
        return y;
    }

    public static void main(String[] args) {
        BSTreeB t = new BSTreeB();
        int NUM = 20;
        int GAP = 7;
        for (int i = GAP; i != 0; i = (i + GAP) % NUM)
            t.insert(i);
        t.print(t.root);
        for (int i = 1; i < NUM; i += 2)
            t.delete(i);
        System.out.println("");
        t.print(t.root);
    }

    private Nodea root;

    class Nodea {
        int element;
        Nodea left;
        Nodea right;
        Nodea parent;

        Nodea(int element) {
            this.element = element;
        }

        Nodea(int element, Nodea left, Nodea right, Nodea parent) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }
}
