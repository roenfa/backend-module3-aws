package org.example.repositories;

import org.example.models.Book;

public interface IBookRepository extends IRepository<Book> {
    Book getById(int id);
}
