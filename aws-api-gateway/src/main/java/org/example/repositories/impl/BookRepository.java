package org.example.repositories.impl;

import org.example.models.Book;
import org.example.repositories.IBookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BookRepository implements IBookRepository {
    private List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    @Override
    public Book save(Book book) {
        this.books.add(book);
        return book;
    }

    @Override
    public Collection<Book> getAll() {
        return this.books;
    }

    @Override
    public Book getById(int id) {
        return this.books.stream()
                .filter( book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
