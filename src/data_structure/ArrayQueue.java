package data_structure;

import java.util.Arrays;

/**
 * 数组实现队列
 */
public class ArrayQueue {
    //队首指针 队尾指针
    private static int head = 0;
    private static int tail = 0;

    private static void enqueue(int[] array, int elem){
        array[tail++] = elem;
    }

    private static int dequeue(int[] array){
        int elem = array[head++];
        return elem;
    }

    private static void print(int[] array){
        for (int i=head; i<tail; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    private static void createQueue(int[] array, int n){
        for (int i=0; i<n; i++){
            array[tail++] = (int)(Math.random()*100);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[20];
        createQueue(array, 10);
        print(array);
        dequeue(array);
        dequeue(array);
        print(array);
        enqueue(array, 89);
        enqueue(array, 28);
        print(array);
    }
}
