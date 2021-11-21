# `BinarySearchTree`

> 主要是写了一些二叉搜索树的基本操作,插入,查找,删除.模仿别人的,自己重新默写一遍.

文件名 | 补充
-------|----
[BinarySearchTree](./BinarySearchTree.java) | 参考自《数据结构与算法-Java语言描述  陈越翻译》
[BSTreeB](./BSTreeB.java) | 参考自《数据结构——从概念到C++实现（第3版）》  但是我把它变成Java语言了。[C++语言版的可以参考我之前写过的博客](https://www.cnblogs.com/coderon/p/13562844.html)
[BSTree](./BSTree.java) | 这是我根据`BinarySearchTree`自己重新默写敲了一遍加深记忆

## `Insert`
> 我的Insert基本和示例的一样，因此下面的代码只写一遍。
> 一些要点是，要使用**递归**，并且用好**返回值**
```java
public void insert(Integer element) {
    root = insert(element, root);
}

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
```

## `findmin` && `findmax`

**循环实现**
```java
public Node findmin(Node root) {
    Node ret = null;
    while (root != null) {
        ret = root;
        root = root.left;
    }
    return ret;
}
```

**递归实现**
```java
public Node findminB(Node root) {
    if (root == null || root.left == null)
        return root;
    return findmin(root.left);
}
```
找最小/最大值，都是往最左边，或者最右边找，所以循环递归都容易实现。总的来说和BinarySearchTree差不多，所以就只贴一遍代码。

## `height`
> 需要注意一点，当一个节点都没有时，树的高度为-1。当只有一个根节点时，树的高度为0。
```java
public int height(Node root) {
    if (root == null)
        return -1;
    return Math.max(height(root.left), height(root.right)) + 1;
}
```


## `print`
> 遍历一棵树，这里以中序遍历为例

```java
public void remove(Integer element) {
    root = remove(element, root);
}
```

```java
    private void print(Node root) {

        if (root == null)
            return;

        print(root.left);
        System.out.println(root.element);
        print(root.right);
    }
```

利用递归写起来很简洁，而且容易记住。至于循环实现，我倒是没有试过。

## `remove`

**我默写的`remove`**
```java
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
```

**示例中简洁的`remove`**
```java
private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
    if (t == null)
        return t; // Item not found; do nothing

    int compareResult = x.compareTo(t.element);

    if (compareResult < 0)
        t.left = remove(x, t.left);
    else if (compareResult > 0)
        t.right = remove(x, t.right);
    else if (t.left != null && t.right != null) // Two children
    {
        t.element = findMin(t.right).element;
        t.right = remove(t.element, t.right);
    } else
        t = (t.left != null) ? t.left : t.right;
    return t;
}
```

我写的`remove`显得很冗余，有4个return。关键是在于没有把找到之后的情况，进行一个有效的分类。

一共有4种情况
- `left ==null,right ==null`
- `left !=null,right ==null`
- `left ==null,right !=null`
- `left !=null,right !=null`

我的代码是这样划分的
> 出发点是，如果右子树不为null,都从右子树找最小的节点。
```java
if (root.left != null || root.right != null) {
    Node ret = findmin(root.right);
    if (ret == null) { //----> left !=null,right ==null
        return root.left;
    }
    int x = ret.element;
    root.element = x;
    root.right = remove(x, root.right); // ----> left ==null,right !=null 和left !=null,right !=null 。注意，这里进行的是一个伪替换。
    return root;
} else           //----> left ==null,right ==null
    return null;
```

示例代码是这样划分的
> 这里只分了两种情况。1、两个子树都不为空，则进行一个**伪替换**。2、其它，哪边不空，则哪边接上，如果两边都空了，直接选一个即可。
```java
else if (t.left != null && t.right != null) // Two children
{
    t.element = findMin(t.right).element;
    t.right = remove(t.element, t.right);
} else
    t = (t.left != null) ? t.left : t.right;
```

## 这样测试

```java
BSTree t = new BSTree();
int NUM = 10;
int GAP = 3;

for (int i = GAP; i != 0; i = (i + GAP) % NUM)
    t.insert(i);
```

这是示例代码中的测试程序，令我最惊讶的是`i`居然把1-NUM走完了。

**我猜的一个规律是，A和B两个数，假设A是小的，并且A和B的最大公约数是1，那么A自增A，再对NUM求余，就可以把1-NUM遍历完。**


