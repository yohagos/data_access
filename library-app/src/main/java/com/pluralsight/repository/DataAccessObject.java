package com.pluralsight.repository;

import java.util.List;
import java.util.Optional;

public interface DataAccessObject<T> {
    List<T> findAll();

    Optional<T> findByID(long id);

    T create(T t);

    T update(T t);

}
