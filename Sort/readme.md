## 十大排序
   排序   | 平均时间复杂度 | 最差时间复杂度 | 最好时间复杂度 |   其它    
 :------: | :------------: | :------------: | :------------: | :-------: 
 快速排序 |  O(n*log(n))   |     O(n^2)     |  O(n*log(n))   | 1. 不稳定 



### 快速排序
> 我觉得是升级版的冒泡排序 [快速排序例子](https://www.cnblogs.com/coderon/p/13407470.html)

**口述快排**
1. 先选定一个轴值pivot
2. 通过一个partition分隔函数，把数据分成两半，一半比pivot小，一半比pivot大。
3. 对两个分区**递归**下去，重复1、2步骤。直到分区不能再分，就递归上来。


**关键** :
- **递归**
- **双重while循环**
- **双指针**


#### 我默写过的快排

```java
private static void quickSort(int pre, int back) {
    if (pre > back)
        return;
    int i = partition(pre, back);
    quickSort(pre, i - 1);
    quickSort(i + 1, back);
}

private static int partition(int pre, int back) {
    int pivot = pre;

    while (pre < back) {
        while (pre < back && array[pivot] < array[back])
            back--;
        if (back > pre) {
            int tmp = array[back];
            array[back] = array[pivot];
            array[pivot] = tmp;
            pivot = back;
            // back--;
        }

        // pre++;
        while (pre < back && array[pivot] > array[pre])
            pre++;
        if (pre < back) {
            int tmp = array[pre];
            array[pre] = array[pivot];
            array[pivot] = tmp;
            pivot = pre;
        }

    }
    return pivot;
}

```
- 确确实实定义了 pivot,pre,back
- 并且pivot确确实实来回移动了许多次，**所以这是最简朴的一个快排**
- `QuickSort`退出递归，为什么不添加=号？如果相等了，就一个值，也没有必要递归下去了。
- `partition`中，要注啥时候跳出循环。简单来说`partition`会**右左右左** 地扫描，把大的放到右边，小的自然回到左边。(降序则相反)
- **右左右左** 的扫描是挺奇妙的
- 但是需要注意到,pivot会和pre和back有重合的可能，但是我不追求优化，只追求能正确地写出快排，这点可以暂时忽略，但是以后会优化。

#### 牛友快排A


```c
void quickSort(int first, int last)
{
    int i = Partition(first, last);
    if (i - 1 > first)
        quickSort(first, i - 1);
    if (i + 1 < last)
        quickSort(i + 1, last);
    return;
    /* 你还可以这样写，更加简洁一些 */
    // if (first >= last) return;
    // int i = Partition(first, last);
    // quickSort(first, i - 1);
    // quickSort(i + 1, last);
}
```

概要:
- 右边遍历、交换
- 左边遍历、交换
- 要注意两个while的判断条件，以及if的判断条件

 本质就是把大的放一边，小的自然会在另一边。把小的放一边，大的自然会在另一边。

```c
int partition(int pre, int back) {
    while (pre < back) {
        while (pre < back && array[pre] < array[back])
            back--;
        if (back > pre) {
            int tmp = array[back];
            array[back] = array[pre];
            array[pre] = tmp;
        }
        while (pre < back && array[pre] < array[back])
            pre++;
        if (pre < back) {
            int tmp = array[back];
            array[back] = array[pre];
            array[pre] = tmp;
        }
    }
    return pre;
}
```

优化的地方在于，不用特定一个pivot。而是把pre或者back作为返回值。**因为第一种解法中，pivot会和pre和back重合，所以也可以把这个利用起来，顺便把pivot干掉。**





#### 牛友快排B

**快排只有一种写法吗？**

首先，快排的思想是不变的，轴值，分区，递归。上面的代码，轴值的左边确确实实比轴值小，右边确确实实比轴值大。**并且**轴值一开始跳来跳去。我发现有一种轴值不用跳来跳去，最后才跳一下的写法。

**虽然代码量会少一点，但是声明的变量会多一点** 


```java
public class QuickSort implements IArraySort {

    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        return quickSort(arr, 0, arr.length - 1);
    }

    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
```

index是慢慢扩张的，index左边的就是比pivot小的，index右边就是比pivot大的。(这是升序的情况 )

**要注意`i<=right`的条件** ，因为right是下标值。



#### 额外思考

上面的例子中，都是一些数组的例子。那快速排序，比冒泡排序、选择排序、直接插入排序的一些优化点在哪？

1. 冒泡排序、选择排序的复杂度都是稳定的O(n^2)，原因是它们对数据的处理是从n、n-1、n-2....1，而快排则是二分了，**大多数**数据处理是从n、n/2、n/4....1，所以平均复杂度是O(n*log(n))
2. 快速排序比直接排序优势的地方，也是一个平均复杂度方面。但是如果遇上数据小，或者本身就有序了，直接排序会更好。(这里说得不是很清楚，了解直接排序会更容易理解)



#### 复杂度

> [快速排序的时间空间复杂度分析](https://zhuanlan.zhihu.com/p/341201904)

看到表格上写着最好的复杂度是O(nlog(n))，最坏的时间复杂度是O(n^2)，那为啥平均复杂度也是O(nlog(n))?

其实有数学公式可以证明，但是我没有深究。



**快速排序一些可以改进的地方**

1. 将快速排序的递归执行改为非递归执行
2. 当问题规模 ![[公式]](https://www.zhihu.com/equation?tex=n) 较小时 ![[公式]](https://www.zhihu.com/equation?tex=%28n+%5Cle+16%29) ,采用直接插入排序求解
3. 每次选取 ![[公式]](https://www.zhihu.com/equation?tex=%5Cfrac%7BE%5Bfirst%5D%2BE%5BLast%5D%7D%7B2%7D) 或 ![[公式]](https://www.zhihu.com/equation?tex=%5Cfrac%7BE%5Bfirst%5D%2BE%5Blast%5D%2BE%5B%28first%2Blast%29%2F2%5D%7D%7B3%7D) 作为 ![[公式]](https://www.zhihu.com/equation?tex=prior)

前两个点我还是懂的，第三个点也知道它的一个必要性，只是不会证明。