package org.gumiho.rest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    protected Article() {}

    public Article(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("name:%s", name);
    }
}
