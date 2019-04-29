package com.algorithm.comparesort;

import java.util.Arrays;

/**
 *数组的随机排列实现
 * 1，用一个随机的优先级数组来重排数组
 * 2，原地排列数组，实现随机
 */
public class ArraysRandom {
    /**
     * 为数组的每个元素 A[i] 赋一个随机的优先级 P[i] ,然依据优先级的大小对数组A中的数据进行排序
     * @param array 给定一个数组
     */
    private void priorityArrayArrangement(int[] array){
        int n = array.length;
        int[] priorityArray = new int[n];
        for(int indexPriorityArray=0; indexPriorityArray<n; indexPriorityArray++){
            //这里用 n 的三次方，是为了让 priorityArray 中的优先级尽可能是唯一的
            priorityArray[indexPriorityArray] = (int)(Math.random()*n*n*n);
        }
        System.out.println();
        for(int i=0; i<n ; i++){
            for(int j=i+1; j<n; j++){
                if(priorityArray[i] > priorityArray[j]){
                    int swap = priorityArray[i];
                    priorityArray[i] = priorityArray[j];
                    priorityArray[j] = swap;

                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
//        for(int index : priorityArray){
//            System.out.print(index + " ");
//        }
//        System.out.println();
    }

    /**
     * 原地随机排列
     * @param array 给定的数组
     */
    private void arrangeSelf(int[] array){
        int n = array.length;
        for (int i=0; i<n; i++){
            int index = (int)(Math.random()*(n-i)) + i;
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }


    public static void main(String[] args) {
        ArraysRandom ar = new ArraysRandom();
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
//        for (int i=0; i<10; i++){
//            ar.priorityArrayArrangement(array);
//            System.out.println(Arrays.toString(array));
//        }
        for (int j=0; j<10; j++){
            ar.arrangeSelf(array);
            System.out.println(Arrays.toString(array));
        }
    }
}
