public class BubbleSort {
    public static void main(String[] args) {

        int[] nums = { 1, 2, 3, 4, 5, 6, 7 };
        BubbleSortA(nums);
        System.out.println();
        int[] numsB = { 4, 5, 3, 2, 6, 7, 8, 9 };
        BubbleSortB(numsB);
    }

    private static void BubbleSortA(int[] nums) {
        int length = nums.length;
        //用于标识是否已经将序列排好序
        boolean isOrdered = false;
        for (int i = 0; i < length - 1; i++) {
            //每一趟开始前都假设已经有序
            System.out.println("执行" + (i + 1) + "轮");
            isOrdered = true;
            for (int j = 0; j < length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    //如果出现有元素交换，则表明此躺可能没有完成排序
                    isOrdered = false;
                }
            }
            //如果当前趟都没有进行元素的交换，证明前面一趟比较已经排好序
            //直接跳出循环
            if (isOrdered) {
                break;
            }
        }

        for (int i : nums) {
            System.out.print(i);
        }
    }

    private static void BubbleSortB(int[] nums) {
        int length = nums.length;
        //用于标识是否已经将序列排好序
        boolean isOrdered = false;
        int lastExchangeIndex = 0;
        //当前趟无序的边界
        int unorderedBorder = length - 1;
        for (int i = 0; i < length - 1; i++) {
            System.out.println("执行" + (i + 1) + "轮");
            //每一趟开始前都假设已经有序
            isOrdered = true;
            for (int j = 0; j < unorderedBorder; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    //如果出现有元素交换，则表明此躺没有完成排序
                    isOrdered = false;
                    //记录下最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }
            unorderedBorder = lastExchangeIndex;
            if (isOrdered) {
                break;
            }
        }

        for (int i : nums) {
            System.out.print(i);
        }
    }
}
/**
 * 
BubbleSortA(nums)执行结果
执行1轮
1234567

BubbleSortB(numsB)执行结果
执行1轮
执行2轮
执行3轮
执行4轮
23456789
 */