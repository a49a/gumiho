package org.gumiho.rest.entity;

import javax.persistence.*;

@Entity
//@Table(name = "foo_article")
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
