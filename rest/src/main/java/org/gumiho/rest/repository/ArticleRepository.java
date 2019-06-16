package org.gumiho.rest.repository;

import org.gumiho.rest.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findByName(String name);

}
