package org.example.config;

import org.example.models.Book;
import org.example.models.Transaction;
import org.example.repositories.ITransactionRepository;
import org.example.repositories.impl.BookRepository;
import org.example.repositories.IBookRepository;
import org.example.repositories.impl.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoriesConfigurator {
    @Bean
    public IBookRepository initBookRepository() {
        var books = new BookRepository();
        books.save(new Book(1, "Effective java"));
        books.save(new Book(2, "Running Spring in Serverless"));
        books.save(new Book(3, "book3"));
        books.save(new Book(4, "book4"));
        books.save(new Book(5, "book5"));
        books.save(new Book(6, "book6"));
        books.save(new Book(7, "book7"));
        books.save(new Book(8, "book8"));
        books.save(new Book(9, "book9"));
        books.save(new Book(10, "book10"));
        return books;
    }

    @Bean
    public ITransactionRepository initTransactionRepository() {
        return new TransactionRepository();
    }

}
