package org.example.controllers;

import lombok.Getter;
import org.example.models.Transaction;
import org.example.repositories.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collection;
import java.util.Collections;

@EnableWebMvc
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private ITransactionRepository repository;

    public TransactionController(ITransactionRepository transactionRepository) {
       this.repository = transactionRepository;
    }

    @GetMapping
    public Collection<Transaction> getTransactions() {
        return this.repository.getAll();
    }

    @GetMapping("{id}")
    public Transaction getTransactionById(@PathVariable String id) {
        return this.repository.getById(id);
    }

    @PostMapping
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return this.repository.save(transaction);
    }


}
