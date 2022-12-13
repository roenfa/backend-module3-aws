package org.example.controllers;


import org.example.models.Book;
import org.example.repositories.IBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private IBookRepository repository;

    public BookController(IBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/books")
    public Collection<Book> getBooks() {
        return this.repository.getAll();
    }

    @RequestMapping(path = "/books", method = RequestMethod.POST)
    public Book saveBook(@RequestBody Book book) {
        logger.info("Request post");
        return this.repository.save(book);
    }
}
