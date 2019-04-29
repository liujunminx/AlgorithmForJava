package com.algorithm.comparesort;

/**
 * 快速排序
 */
public class QuickSort {
    private void quickSort(int[] array, int start, int end){
        if (start < end){
            int mid = partition(array, start ,end);
            quickSort(array, start,mid - 1);
            quickSort(array, mid + 1, end);
        }
    }

    private int partition(int[] array, int start, int end){
        //把数组最后一个数作为比较的数
        int key = array[end];
        //从数组前面开始进入
        int compare = start - 1;
        //数组里面的元素全部相等
        boolean flag = true;
        for (int index = start; index < end; index++){
            if (array[index] < key){
                flag = false;
                compare++;
                int temp = array[compare];
                array[compare] = array[index];
                array[index] = temp;
            }
        }
        int temp = array[compare + 1];
        array[compare + 1] = array[end];
        array[end] = temp;

        if (flag){
            return (start + end) / 2;
        }

        return compare + 1;
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] array = new int[]{
                11, 11, 11, 11, 11, 11, 11
        };
    }
}
