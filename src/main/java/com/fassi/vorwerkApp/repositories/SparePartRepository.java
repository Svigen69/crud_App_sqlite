package com.fassi.vorwerkApp.repositories;

import com.fassi.vorwerkApp.core.CrudRepository;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.SparePart;
import com.fassi.vorwerkApp.services.SqliteServices;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

public class SparePartRepository implements CrudRepository<SparePart> {
    public static void init() throws Exception {

        sparePartDao = DaoManager.createDao(SqliteServices.getConnectionSource(), SparePart.class);
        TableUtils.createTableIfNotExists(SqliteServices.getConnectionSource(), SparePart.class);
    }

    private static Dao<SparePart, String> sparePartDao = null;

    @Override
    public List<SparePart> getAll() throws Exception {
        return SparePartRepository.sparePartDao.queryForAll();
    }

    @Override
    public SparePart getOne(int id) throws Exception {
        return SparePartRepository.sparePartDao.queryForId(String.valueOf(id));
    }

    @Override
    public SparePart addOne(SparePart entity) throws Exception {
        SparePartRepository.sparePartDao.create(entity);
        return entity;
    }

    @Override
    public SparePart updateOne(int id, SparePart entity) throws Exception {
        SparePart entity_U = this.getOne(id);
        SparePartRepository.sparePartDao.update(entity);
        return entity;
    }

    @Override
    public SparePart deleteOne(int id) throws Exception {
        SparePart entity = this.getOne(id);
        SparePartRepository.sparePartDao.delete(entity);
        return entity;
    }
}


