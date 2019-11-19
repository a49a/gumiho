package org.gumiho.demo.lang;

import java.io.Serializable;

public class Employee implements Serializable {
    private final int id;
    private final int no;

    public Employee(int id, int no) {
        this.id = id;
        this.no = no;
    }
    public int getId() {
        return id;
    }
    public int getNo() {
        return no;
    }
}
