package com.algorithm.linearsort;

import java.util.Arrays;

/**
 * 计数排序
 * 基数排序
 */
public class CountingSort {
    private void countingSort(int[] array, int[] ans, int key){
        //创建一个临时数组，用来存储0到最大数的区间的所有值
        int[] temp = new int[key + 1];
        for (int i = 0; i <= key; i++){
            temp[i] = 0;
        }
        //把array中所有数出现的次数存储到temp中
        for (int j = 0; j < array.length; j++){
            temp[array[j]]++;
        }
        //temp[i]包含比i小或者是等于i的数
        for (int i = 1; i <= key; i++){
            temp[i] += temp[i-1];
        }
        for (int j = array.length - 1; j >= 0; j--){
            ans[temp[array[j]] - 1] = array[j];
            temp[array[j]]--;
        }
    }

    private int getMaxInArray(int[] array){
        int max = -1;
        for (int i = 0; i < array.length; i++){
            if (array[i] > max)
                max = array[i];
        }
        return max;
    }

    private int[] sort(int[] array){
        int[] ans = new int[array.length];
        int key = getMaxInArray(array);
        countingSort(array, ans, key);
        return ans;
    }

    private void radixSort(int[] array, int d){
        int n = 1;//代表位数对应的数：１，１０，１００
        int k = 0;//保存每一位排序后的结果用于下一位的排序输入
        int length = array.length;
        //排序桶用于保存每次排序后的结果，这一数位里相同的数字放在同一个桶里
        int[][] bucket = new int[10][length];
        //用于保存每个桶里的数字有多少位
        int[] order = new int[10];
        while (n < d){
            for (int num: array
                 ) {
                int pop = (num/n) % 10;
                bucket[pop][order[pop]] = num;
                order[pop]++;
            }
            //每一个数位都有９个数字，即０－９
            for (int i=0; i<10; i++){
                //如果这个数位出现过这个数字
                if (order[i] != 0){
                    //这个数出现了多少次
                    for (int j=0; j<order[i]; j++){
                        array[k] = bucket[i][j];
                        k++;
                    }
                }
                //桶的计数器清零，用于下一次排序
                order[i] = 0;
            }
            n *= 10;
            k = 0;//k置为零，用于下一轮保存结果
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{
                123, 23, 123, 43, 2, 1234, 33
        };
        CountingSort countingSort = new CountingSort();
        countingSort.radixSort(array, 10000);
        System.out.println(Arrays.toString(array));
    }
}
