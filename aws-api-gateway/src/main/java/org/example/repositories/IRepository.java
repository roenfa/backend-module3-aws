package org.example.repositories;

import org.example.models.Book;

import java.util.Collection;

public interface IRepository<T> {
    T save(T object);
    Collection<T> getAll();

}
