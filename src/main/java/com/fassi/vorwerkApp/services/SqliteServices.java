package com.fassi.vorwerkApp.services;


import com.fassi.vorwerkApp.repositories.ClientRepository;
import com.fassi.vorwerkApp.repositories.MeetingRepository;
import com.fassi.vorwerkApp.repositories.ProductRepository;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.io.IOException;

import com.j256.ormlite.support.ConnectionSource;

public class SqliteServices {
    private static ConnectionSource connectionSource = null;

    public static ConnectionSource getConnectionSource() throws Exception {
        if (SqliteServices.connectionSource == null) {
            throw new Exception("Service not initialised Exception");
        }
        return SqliteServices.connectionSource;
    }

    public static void init(String ressourceString) throws Exception {
        if (SqliteServices.connectionSource == null) {
            SqliteServices.connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + ressourceString);
            ClientRepository.init();
            ProductRepository.init();
            MeetingRepository.init();
        }
    }

    public static void closeConnectionSource() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
