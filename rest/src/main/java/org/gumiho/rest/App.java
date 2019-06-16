package org.gumiho.rest;


import org.gumiho.rest.entity.Article;
import org.gumiho.rest.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner demo(ArticleRepository repository) {
        return args -> {
            repository.save(new Article("foo"));
            repository.save(new Article("bar"));
            for(Article article: repository.findAll()) {
                log.info(article.toString());
            }
        };
    }
}
