package org.example.config;

import org.example.models.Book;
import org.example.repositories.BookRepository;
import org.example.repositories.IBookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoriesConfigurator {
    @Bean
    public IBookRepository initBookRepository() {
        var books = new BookRepository();
        books.save(new Book(1, "Effective java"));
        books.save(new Book(2, "Running Spring in Serverless"));

        return books;
    }
}
