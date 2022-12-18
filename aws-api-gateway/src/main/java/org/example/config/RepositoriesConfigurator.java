package org.example.config;

import org.example.models.Book;
import org.example.models.Transaction;
import org.example.repositories.BookRepository;
import org.example.repositories.IBookRepository;
import org.example.repositories.ITransactionRepository;
import org.example.repositories.TransactionRepository;
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
    @Bean
    public ITransactionRepository initTransactionRepository(){
        var transactions = new TransactionRepository();
        Transaction t1 = new Transaction("2163962193", "REFUND", 21.3,"2022-12-16");
        Transaction t2 = new Transaction("2163962194", "SALES", 25.6,"2022-12-17");
        Transaction t3 = new Transaction("2163962195", "EXPECTED", 27.8,"2022-12-18");
        transactions.save(t1);
        transactions.save(t2);
        transactions.save(t3);

        return transactions;
    }
}
