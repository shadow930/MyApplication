package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        List array = new ArrayList<Integer>();
        int index = 0;
        while (sc.hasNextInt()) {
            index++;
            int input = sc.nextInt();
            array.add(input);
            if (index >= 5) {
                break;
            }
        }
        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum = sum + (int) array.get(i);
        }
        int average = sum / 5;
        int moveCount = 0;
        for (int i = 0; i < array.size(); i++) {

            if ((int) array.get(i) > average) {
                int content = (int) array.get(i);
                while (true) {
                    content--;
                    moveCount++;
                    System.out.println("----" + moveCount);
                    if (content == average) {
                        break;
                    }
                }
            }


            if ((int) array.get(i) < average) {
                int content = (int) array.get(i);
                while (true) {
                    content++;
                    moveCount++;
                    System.out.println("++++" + moveCount);
                    if (content == average) {
                        break;
                    }
                }
            }

        }
        System.out.println(moveCount);
    }

    static class MyRunable implements Runnable {
        private static MyRunable instance;

        private MyRunable() {
        }

        public static MyRunable getInstance() {
            synchronized (MyRunable.class) {
                if (instance != null) {
                    instance = new MyRunable();
                }
            }
            return instance;
        }

        @Override
        public void run() {
//            synchronized (Test.class) {
            System.out.println("Hello World!" + Thread.currentThread());
//            }
        }
    }
}
