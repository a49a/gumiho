package org.gumiho.demo.thread;


public class SynchronizedDemo {
    int count = 400;
    int n = count;
    public void minus() {
        for (int i = 0; i < n; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + ":" + count);
        }
    }
}
