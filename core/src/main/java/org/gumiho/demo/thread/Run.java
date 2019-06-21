package org.gumiho.demo.thread;

public class Run {
    public static void main(String[] args) {
        final SynchronizedDemo sync = new SynchronizedDemo();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                sync.minus();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                sync.minus();
            }
        });
        t1.start();
        t2.start();
    }
}
