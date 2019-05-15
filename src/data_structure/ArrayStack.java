package data_structure;

import java.util.Arrays;
import java.util.Random;

/**
 * 数组栈
 */
public class ArrayStack {
    //栈顶指针
    private static int top = 0;

    private static boolean stackEmpty(){
        if (top == 0)
            return true;
        return false;
    }

    private static void push(int[] array, int elem){
        array[top++] = elem;
    }

    private static int pop(int[] array){
        if (stackEmpty())
            throw new RuntimeException("underflow");
        return array[--top];
    }

    private static void print(int[] array){
        for (int i=0; i<top; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    private static void createStack(int[] array, int n){
        for (int i=0; i<n; i++){
            array[top++] = (int)(Math.random()*100);
        }
        System.out.println("top = " + top);
    }

    public static void main(String[] args) {
        int[] array = new int[20];
        createStack(array, 10);
        print(array);
        push(array, 11);
        push(array, 20);
        print(array);
        pop(array);
        print(array);
    }
}
