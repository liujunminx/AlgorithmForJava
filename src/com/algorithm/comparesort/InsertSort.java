package com.algorithm.comparesort;

import java.util.Arrays;

/**
 * {@code InsertSort} 插入排序
 * 主要运用循环不变式推断出算法的正确性
 * @author liujunmin
 * @deprecated
 */
public class InsertSort {
    //插入排序，非升序排列
    private int[] insertSort1(int[] A){
        //从已经排列的数组后面挨个添加无序的数据
        for(int j=1;j<A.length;j++){
            int key=A[j];//保存被插入的变量
            int i=j-1;//i是j的前驱
            while(i>=0&&A[i]<key){//如果数字很大，排到前面去
                A[i+1]=A[i];//数组元素后移
                i--;//再去前一个元素比较
            }
            A[i+1]=key;//更改当前i的后驱，即正确插入的位置
        }
        return A;
    }

    private int[] insertSort(int[] A) {
        //A[0,n]排序，A[0,j]是它的子集
        //for j<- 1 to length(A)
        for (int j = 1; j < A.length; j++) {
            //do key<- A[j]
            int key = A[j];
            //i<- j-1
            int i = j - 1;//key的前驱
            //while i>=0 and A[j]>key
            while (i >= 0 && A[i] > key) {//如果前驱比key大
                //A[j+1]<- A[j]
                A[i + 1] = A[i];//下标后移
                //i<- i-1
                i--;//往前查找比它更小的
            }
            //A[i+1]<- key
            A[i + 1] = key;
        }
        return A;
    }

    //推断出冒泡算法
    private void insertSort2(int[] array){
        for(int j=1;j<array.length;j++){
            for(int i=0;i<j;i++){
                if(array[i]>array[j]){
                    int temp=array[i];
                    array[i]=array[j];
                    array[j]=temp;
                }
            }
        }
    }


    /**
     * 直接用数组而不用key保存数据的做法不可取，因为数组后移时，原数组的元素会被取代
     * @param arr
     */
//    private int[] insertSort2(int[] array){
//        for(int j=1;j<array.length;j++){
//            int i=j-1;
//             while(i>=0&&array[i]>array[j]){
//                array[i+1]=array[i];
//                i--;
//            }
//            array[i+1]=array[j];
//        }
//        return array;
//    }

    private void print(int[] arr){
        for (int a:arr
             ) {
            System.out.print(a+" ");
        }
        System.out.println();
    }

    /**
     * A<- [0,n]
     * B<- [0,n]
     * C<- [0,n+1]
     * carry<- 0
     * al<- length(A)-1
     * bl<- length(B)-1
     * cl<- length(C)-1
     * while al>=o and bl>=0
     *      sum<- carry+A[al]+B[bl]
     *      carry=sum/2
     *      C[cl]<- sum%2
     * if carry>0
     *      C[0]=1
     * @param A
     * @param B
     * @return
     */
    private int[] binarySummation(int[] A,int[] B){
        int[] C=new int[A.length+1];
        int aLength=A.length-1;
        int bLength=B.length-1;
        int cLength=C.length-1;
        int carry=0;
        while(aLength>=0&&bLength>=0){
            int sum=carry+A[aLength]+B[bLength];
            carry=sum/2;
            C[cLength]=sum%2;
            aLength--;
            bLength--;
            cLength--;
        }
        if(carry>0){
            C[0]=1;
        }
        return C;
    }

    /**
     * 下面时递归版本的直接插入排序
     *  插入第n个元素
     */
    private void recursiveInsert(int[] array,int index){
        int key=array[index];
        int i=index-1;
        while(i>=0&&array[i]>key){
            array[i+1]=array[i];
            i--;
        }
        array[i+1]=key;
    }

    private void resursiveInsertSort(int[] array,int n){
        if(n>0){
            resursiveInsertSort(array,n-1);//前面已经排好序了
            recursiveInsert(array,n);
        }
        return;//返回递归前的上一个函数
    }

    /**
     * 在已经排好序的情况下
     * 由分治法得出的二分查找法 返回查找到的下标
     * @param array
     */
    private int binarySearch(int[] array,int key){
        int low=0;
        int high=array.length-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(array[mid]==key) return mid;
            if(array[mid]>key) high=mid-1;
            if(array[mid]<key) low=mid+1;
        }
        return -1;
    }

    /**
     * 2.3-6 在插入排序中的反扫描的while部分优化为二分查找
     * @param args
     */
//    private int binaryInsert(int[] array,int left,int right,int key){
//        int low=left;
//        int high=right-1;
//        int mid=0;
//        while(low<high){
//            mid=(low+high)/2;
//            if(array[mid]==key) return mid+1;
//            if(array[mid]<key) low=mid+1;
//            if(array[mid]>key) high=mid-1;
//        }
//        return low;
//     }
//
//    private void binaryInsertSort(int[] array){
//        for(int j=1;j<array.length;j++){
//            int key=array[j];
//            int index=binaryInsert(array,0,j,key);
//        }
//    }

    public static void main(String[] args) {
        InsertSort insertSort=new InsertSort();
        int[] array={4,342,23,43,22,13,45,342,12};
        System.out.println(Arrays.toString(array));
        int index=insertSort.binarySearch(array,23);
        System.out.println(index);
    }
}
