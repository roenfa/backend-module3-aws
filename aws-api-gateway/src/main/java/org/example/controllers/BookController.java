package org.example.controllers;


import org.example.models.Book;
import org.example.repositories.IBookRepository;
import org.example.repositories.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collection;

@EnableWebMvc
@RestController
@RequestMapping("books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private IBookRepository repository;

    public BookController(IBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Collection<Book> getBooks() {
        return this.repository.getAll();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable int id) {
        return this.repository.getById(id);
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        logger.info("Request post");
        return this.repository.save(book);
    }

}
