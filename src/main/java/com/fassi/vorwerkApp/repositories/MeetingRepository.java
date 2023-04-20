package com.fassi.vorwerkApp.repositories;

import com.fassi.vorwerkApp.core.CrudRepository;
import com.fassi.vorwerkApp.models.Client;

import com.fassi.vorwerkApp.models.Meeting;
import com.fassi.vorwerkApp.services.SqliteServices;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingRepository implements CrudRepository<Meeting> {
    static private SqliteServices sqliteServices;

    public static void init() throws Exception {
        meetingDao = DaoManager.createDao(SqliteServices.getConnectionSource(), Meeting.class);
        TableUtils.createTableIfNotExists(SqliteServices.getConnectionSource(), Meeting.class);
    }

    private static Dao<Meeting, String> meetingDao = null;

    @Override
    public List<Meeting> getAll() throws Exception {
        return MeetingRepository.meetingDao.queryForAll();
    }

    @Override
    public Meeting getOne(int id) throws Exception {
        return MeetingRepository.meetingDao.queryForId(String.valueOf(id));
    }

    public List<Meeting> findBy(String firstName, String lastName) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("Vorname", firstName);
        map.put("Nachname", lastName);
        try {
            return MeetingRepository.meetingDao.queryForFieldValues(map);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Meeting addOne(Meeting entity) throws Exception {
        MeetingRepository.meetingDao.create(entity);
        return entity;
    }

    @Override
    public Meeting updateOne(int id, Meeting entity) throws Exception {
        Meeting entity_U = this.getOne(id);
        entity.setId((entity_U).getId());
        MeetingRepository.meetingDao.update(entity);
        return entity;
    }

    @Override
    public Meeting deleteOne(int id) throws Exception {
        Meeting entity = this.getOne(id);
        MeetingRepository.meetingDao.delete(entity);
        return entity;
    }

}
