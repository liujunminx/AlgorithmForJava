package sort.comparesort;

import java.util.Arrays;

/**
 * 分治法：合并排序
 * @author liujunmin
 */
public class MergeSort {

    private void sort(int[] array){
        int[] temp=new int[array.length];
        mergeSort1(array,0,array.length-1,temp);
    }

    private void mergeSort1(int[] array, int left, int right, int[] temp){
        if(left < right){
            int mid = (left+right) / 2;
            mergeSort1(array, left, mid, temp);
            mergeSort1(array, mid+1, right, temp);
            merge1(array, left, mid, right, temp);
        }
    }
    /**
     * 2.3-2    修改merge过程，使不使用哨兵元素，而是在一旦数组L或者R中的所有元素都被复制回数组后A后，就立即停止，再使得剩下的另一个数组中
     * 余下的数组元素复制回数组中
     * @param array
     * @param left
     * @param mid
     * @param right
     */
    private void merge1(int[] array,int left,int mid,int right,int[] temp){
        int i = left;//左序列指针
        int j = mid + 1;//右序列指针
        //创建一个临时数组指针
        int t = 0;
        //左右数组其中一组元素都被复制完成
        while(i <= mid && j <= right){
            if(array[i] <= array[j]){
                temp[t++] = array[i++];
            }else{
                temp[t++] = array[j++];
            }
        }
        //左边数组未被复制完，直接复制
        while(i <= mid)   temp[t++] = array[i++];
        //右边数组未被复制完
        while(j <= right) temp[t++] = array[j++];
        t = 0;
        while (left <= right){
            array[left++] = temp[t++];
        }
    }

    /**
     * 分治法实现排序
     * @param array 合并成的数组
     * @param left  第一个元素
     * @param mid   中位数
     * @param right 末尾元素
     */
    private void merge(int[] array,int left,int mid,int right){
        //把数组大小分为n1,n1两块，
        int n1=mid-left+1;
        int n2=right-mid;
        //array[left,right]<- leftArray[left,mid] and rightArray[mid+1,right]
        int[] leftArray=new int[n1+1];
        int[] rightArray=new int[n2+1];
        //把以上区间一一对应的复制到数组中
        for(int i=0;i<n1;i++){
            leftArray[i]=array[left+i];
        }
        for(int i=0;i<n2;i++){
            rightArray[i]=array[mid+1+i];
        }
        //设置哨兵
        leftArray[n1]=Integer.MAX_VALUE;
        rightArray[n2]=Integer.MAX_VALUE;
        int leftIndex=0;
        int rightIndex=0;
        //从left开始，rigth结束
        for(int k=left;k<=right;k++){
            //一个一个的比较大小，两个数中较小的放入数组中
            if(leftArray[leftIndex]<=rightArray[rightIndex]){
                array[k]=leftArray[leftIndex++];
            }else{
                array[k]=rightArray[rightIndex++];
            }
        }
    }

    /**
     * 分治，先把元素一个个的分开，然后再逐渐的合并
     * @param array
     * @param left
     * @param right
     */
    private void mergeSort(int[] array,int left,int right){
        //当left》=right时说明有且只有一个元素了
        if(left<right){
            int mid=(left+right)/2;
            mergeSort(array,left,mid);//左半边递归
            mergeSort(array,mid+1,right);//右半边递归
            merge(array,left,mid,right);//合并a
        }
    }

    //二分查找
    private int binarySearch(int[] array,int start,int end,int key){
        int low=start;
        int high=end-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(array[mid]==key) return mid;
            if(array[mid]>key) high=mid-1;
            if(array[mid]<key) low=mid+1;
        }
        return -1;
    }

    //二分法求两数之和
    //利用二分查找快速定位
    private void binaryAddTwoNum(int[] array,int sum){
        for(int i=0;i<array.length;i++){
            //二分查找找另个相近的数的下标
            int temp=binarySearch(array,i,array.length,sum-array[i]);//从第一个开始找最符合的数字 即寻找各种可能的组合
            if(temp!=i&&temp!=-1){
                System.out.println(array[temp]+" "+array[i]);
            }
        }
    }

    //冒泡排序
    private void BubbleSort(int[] array){
        for(int i=0;i<array.length;i++){
            //从后往前找
            for(int j=array.length-1;j>=i+1;j--){
                if(array[i]>array[j]){
                    int temp=array[i];
                    array[i]=array[j];
                    array[j]=temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        MergeSort ms=new MergeSort();
        int[] array={54,333,46,2,44,32,43,55,67,88,434};
        ms.BubbleSort(array);
        System.out.println(Arrays.toString(array));
    }
}
