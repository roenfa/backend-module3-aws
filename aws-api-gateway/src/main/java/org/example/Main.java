package org.example;

import org.example.config.RepositoriesConfigurator;
import org.example.controllers.BookController;
import org.example.controllers.TransactionController;
import org.example.models.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BookController.class, RepositoriesConfigurator.class, TransactionController.class, Transaction.class})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}