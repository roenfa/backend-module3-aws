package org.example.controllers;

import java.util.Collection;

import org.example.models.Transaction;
import org.example.repositories.ITransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private ITransactionRepository repository;

  @GetMapping(path = "/transactions")
  public Collection<Transaction> geTransactions(){
    return this.repository.getAll();
  }

  @GetMapping(path = "/transactions/{params}")
  public Collection<Transaction> getTransaction(@PathVariable("params") String params){
    return this.repository.getBy(params);
  }

  @RequestMapping(path = "/transactions", method = RequestMethod.POST)
  public Transaction saveTransaction(@RequestBody Transaction transaction){
    logger.info("Request post");
    return this.repository.save(transaction);
  }
}