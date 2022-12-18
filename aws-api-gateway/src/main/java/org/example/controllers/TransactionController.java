package org.example.controllers;

import java.util.Collection;

import org.example.models.Transaction;
import org.example.repositories.ITransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class TransactionController {
  private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
  private ITransactionRepository repository;

  public TransactionController(ITransactionRepository repository){
    this.repository = repository;
  }

  @GetMapping(value = "/transactions")
  public Collection<Transaction> getTransactions(){
    return this.repository.getAll();
  }

  @GetMapping("/transactions/{id}")
  public Transaction getTransaction(@PathVariable("id") String id){
    Transaction transaction = this.repository.findById(id);
    return transaction;
  }

  @RequestMapping(path = "/transactions", method = RequestMethod.POST)
  public Transaction saveTransaction(@RequestBody Transaction transaction){
    logger.info("Request post");
    return this.repository.save(transaction);
  }
}
