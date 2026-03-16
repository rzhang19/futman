package com.futman.db;

import java.util.List;

public interface DAO<T> {
    boolean save(T entity);
    T findById(int id);
    List<T> findAll();
    boolean update(T entity);
    boolean delete(int id);
}
