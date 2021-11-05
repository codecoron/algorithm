public class QuickSort {
    static int array[] = { 23, 13, 35, 6, 19, 50, 28 };

    public static void main(String[] args) {
        quickSort(0, 6);
        for (int i : array) {
            System.out.printf("%d ", i);
        }
    }

    private static void quickSort(int pre, int back) {
        if (pre > back)
            return;
        int i = partitionB(pre, back);
        quickSort(pre, i - 1);
        quickSort(i + 1, back);
    }

    private static int partition(int pre, int back) {
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

    private static int partitionB(int pre, int back) {
        int pivot = pre;
        int index = pre + 1;

        for (int j = index; j <= back; j++) {
            if (array[pivot] > array[j]) {
                int t = array[j];
                array[j] = array[index];
                array[index] = t;
                index++;
            }
        }
        int t = array[pivot];
        array[pivot] = array[index - 1];
        array[index - 1] = t;
        return index - 1;
    }
}

/**
 * 输出
6 13 19 23 28 35 50
 */