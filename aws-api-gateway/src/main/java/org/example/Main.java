package org.example;

import org.example.config.RepositoriesConfigurator;
// import org.example.controllers.BookController;
import org.example.controllers.TransactionController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// @Import({BookController.class, RepositoriesConfigurator.class})
@Import({TransactionController.class, RepositoriesConfigurator.class})
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        
    }
}