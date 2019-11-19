package org.gumiho.demo.lang;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Delay {
    public static void main(String[] args) {
        Employee employee = new Employee(4, 64);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("template2"));
            oos.writeObject(employee);
            oos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
