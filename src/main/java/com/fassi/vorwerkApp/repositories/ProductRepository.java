package com.fassi.vorwerkApp.repositories;

import com.fassi.vorwerkApp.core.CrudRepository;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.services.SqliteServices;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

public class ProductRepository implements CrudRepository<Product> {

    public static void init() throws Exception {
        productDao = DaoManager.createDao(SqliteServices.getConnectionSource(), Product.class);
        TableUtils.createTableIfNotExists(SqliteServices.getConnectionSource(), Product.class);
    }

    private static Dao<Product, String> productDao = null;


    @Override
    public List<Product> getAll() throws Exception {
        return ProductRepository.productDao.queryForAll();
    }

    @Override
    public Product getOne(int id) throws Exception {
        return ProductRepository.productDao.queryForId(String.valueOf(id));
    }

    @Override
    public Product addOne(Product entity) throws Exception {
        ProductRepository.productDao.create(entity);
        return entity;
    }

    @Override
    public Product updateOne(int id, Product entity) throws Exception {
        Product entity_U = this.getOne(id);
        ProductRepository.productDao.update(entity);
        return entity;
    }

    @Override
    public Product deleteOne(int id) throws Exception {
        Product entity = this.getOne(id);
        ProductRepository.productDao.delete(entity);
        return entity;
    }
}
