package org.gumiho.demo.lang;


import java.io.Serializable;

public class Genericity {
    public static void main(String[] args) {
        String middle = getMiddle("a", "b", "c");
        System.out.println(middle);
    }
    public static  <T> T getMiddle(T... a) {
        return a[a.length / 2];
    }
    public static <T extends Comparable & Serializable> Pair<T, T> minmax(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T min = a[0];
        T max = a[0];

        for (int i = 0; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) {
                min = a[i];
            }
            if (max.compareTo(a[i]) < 0) {
                max = a[i];
            }
        }

        return new Pair<>(min, max);
    }
}
