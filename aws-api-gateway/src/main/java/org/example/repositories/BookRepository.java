package org.example.repositories;

import org.example.models.Book;

import java.util.ArrayList;
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
}
