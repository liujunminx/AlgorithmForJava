package com.algorithm.comparesort;

import java.util.Arrays;

/**
 * 堆排序
 */
public class HeapSort {
    /**
     * 保持堆的性质
     * @param array 原数组
     * @param subRoot   字根节点
     * @param heapSize 堆大小
     */
    private void maxHeapify(int[] array, int subRoot, int heapSize){
        int left = getLeft(subRoot);
        int right = getRight(subRoot);
        int largest = -1;//保存最大值下标
        //如果比根节点大，就互换位置
        //如果左子节点比根节点大
        if (left < heapSize && array[left] > array[subRoot]){
            largest = left;
        }else{
            largest = subRoot;
        }
        //如果右子节点比根节点大
        if (right < heapSize && array[right] > array[largest]){
            largest = right;
        }
        //交换位置
        if ( largest != subRoot){
            int temp = array[subRoot];
            array[subRoot] = array[largest];
            array[largest] = temp;
            //一直递归下去，直到全部排序正确为止
            maxHeapify(array, largest, heapSize);
        }
    }

    /**
     * 返回左子树下标
     * @param subRoot
     */
    private int getLeft(int subRoot){
        return (subRoot << 1) + 1;
    }

    /**
     * 返回右子树下标
     * @param subRoot
     * @return
     */
    private int getRight(int subRoot){
        return (subRoot << 1) + 2;
    }

    /**
     * 返回父母节点下标
     * @param child
     * @return
     */
    private int getParent(int child){
        return child % 2 == 0 ? (child >> 1) - 1 : (child >> 1);
    }

    /**
     * 建立大根堆
     * 自下向顶建堆
     * @param array
     */
    private void buildMaxHeap(int[] array){
        int n = array.length;
        int heapSize = array.length;
        //子数组array [ (|_n/2_| + 1) .... n ] 中的元素都是树中的叶子
        for(int i= n/2; i>=0; i--){
            maxHeapify(array, i, heapSize);
        }
    }

    /**
     * 堆排序时，先把堆中的最大数，也就是根节点取出来，然后在调整堆，保持堆的性质
     * 这样就可以去除下一个最大数，因为你永远取的是根节点，永远最大
     * @param array
     */
    private void heapSort(int[] array){
        int heapSize = array.length;
        buildMaxHeap(array);
        for (int i=array.length-1; i>=1; i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapSize--;
            maxHeapify(array, 0,heapSize);
        }
    }

    /**
     * 返回堆中具有最大关键字的元素
     * @param array
     * @return
     */
    private int heapMaximum(int[] array){
        return array[0];
    }

    /**
     * 去掉并返回堆中具有最大关键字的元素
     * @param array
     * @return
     */
    private int heapExtractMax(int[] array){
        int heapSize = array.length;
        if (heapSize < 0){
            throw new RuntimeException("heap underflow");
        }
        int max = array[0];
        array[0] = array[heapSize-1];
        heapSize--;
        maxHeapify(array, 0, heapSize);
        return max;
    }

    /**
     * 将元素index的关键字增加到key
     * @param array
     * @param index
     * @param key
     */
    private void heapIncreaseKey(int[] array, int index, int key){
        //key的值不能小鱼index元的关键字值
        if (key < array[index]){
            throw new RuntimeException("new key is smaller than current key");
        }
        array[index] = key;
        while (index >= 0 && array[getParent(index)] < array[index]){
            int temp = array[index];
            array[index] = array[getParent(index)];
            array[getParent(index)] = temp;
            index = getParent(index);//更新index,与跟上层的父节点比较
        }
    }

    private void maxHeapInsert(int[] array, int key){
        array = Arrays.copyOfRange(array, 0, array.length + 1);
        int heapSize = array.length;
        heapIncreaseKey(array, heapSize, key);
    }

    public static void main(String[] args) {
        int[] array = new int[]{4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        array = Arrays.copyOfRange(array, 0, array.length+1);
        System.out.println(Arrays.toString(array));
    }
}
