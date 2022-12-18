package org.example.repositories;

import org.example.models.Book;

import java.util.Collection;

public interface IBookRepository  {
    Book save(Book book);
    Collection<Book> getAll();

    Book getById(int id);

}
