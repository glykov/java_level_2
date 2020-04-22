import java.util.*;

public class Lesson05 {
    private static final int size = 10000000;
    private static final int half = size / 2;
    
    private static float[] fillArray() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        return arr;
    }

    private static long oneTreadTime() {
        float[] arr = fillArray();
        long a = System.currentTimeMillis();
        Calculator c = new Calculator(arr);
        // waitng for thread done its calculations
        try {
            while (c.isAlive()) {
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread is interrupted");
        }
        long b = System.currentTimeMillis();
        return b - a;
    }

    private static long twoTreadsTime() {
        Calculator c1;
        Calculator c2;
        float[] arr = fillArray();
        float[] a1 = new float[half];
        float[] a2 = new float[half];

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);
        c1 = new Calculator(a1);
        c2 = new Calculator(a2);
        // waitng for both threads done their calculations
        try {
            while (c1.isAlive() || c2.isAlive()) {
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread is interrupted");
        }
        System.arraycopy(c1.getArray(), 0, arr, 0, half);
        System.arraycopy(c2.getArray(), 0, arr, half, half);
        long b = System.currentTimeMillis();

        return b - a;
    }

    public static void main(String[] args) {
        System.out.println("Calculation time for one thread: " + oneTreadTime());
        System.out.println("Calculation time for two threads: " + twoTreadsTime());
    }
}

class Calculator extends Thread {
    private float[] arr;

    public Calculator(float[] a) {
        arr = a;
        start();
    }

    @Override
    public void run() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public float[] getArray() {
        return arr;
    }
}