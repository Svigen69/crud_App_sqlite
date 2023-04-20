package com.fassi.vorwerkApp.repositories;

import com.fassi.vorwerkApp.core.CrudRepository;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.services.SqliteServices;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository implements CrudRepository<Client> {
    private static Dao<Client, String> clientDao = null;

    static private SqliteServices sqliteServices;

    public static void init() throws Exception {
        clientDao = DaoManager.createDao(SqliteServices.getConnectionSource(), Client.class);
        TableUtils.createTableIfNotExists(SqliteServices.getConnectionSource(), Client.class);
    }

    public List<Client> search(String pattern) throws Exception {
        return ClientRepository.clientDao.query((PreparedQuery<Client>) ClientRepository.clientDao.queryBuilder().where().like("firstName", "%" + pattern + "%").or().like("lastName", "%" + pattern + "%").prepare());
    }

    @Override
    public List<Client> getAll() throws Exception {
        return ClientRepository.clientDao.queryForAll();
    }

    @Override
    public Client getOne(int id) throws Exception {
        return ClientRepository.clientDao.queryForId(String.valueOf(id));
    }

    public List<Client> findBy(String firstName, String lastName) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("Vorname", firstName);
        map.put("Nachname", lastName);
        try {
            return ClientRepository.clientDao.queryForFieldValues(map);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Client addOne(Client entity) throws Exception {
        ClientRepository.clientDao.create(entity);
        return entity;
    }

    @Override
    public Client updateOne(int id, Client entity) throws Exception {
        Client old = this.getOne(id);
        entity.setId(old.getId());
        ClientRepository.clientDao.update(entity);
        return entity;
    }

    @Override
    public Client deleteOne(int id) throws Exception {
        Client entity = this.getOne(id);
        ClientRepository.clientDao.delete(entity);
        return entity;
    }
}