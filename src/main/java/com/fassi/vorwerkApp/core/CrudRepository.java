package com.fassi.vorwerkApp.core;

import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.models.SparePart;

import java.util.List;

public interface CrudRepository<T> {
    List<T> getAll() throws Exception;

    T getOne(int id)throws Exception;

    T addOne(T entity)throws Exception;

    T updateOne(int id, T entity)throws Exception;

    T deleteOne(int id)throws Exception;

}
