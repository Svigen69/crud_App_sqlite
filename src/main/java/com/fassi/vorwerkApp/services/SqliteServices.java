package com.fassi.vorwerkApp.services;

import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.models.SparePart;
import com.fassi.vorwerkApp.repositories.ClientRepository;
import com.fassi.vorwerkApp.repositories.ProductRepository;
import com.fassi.vorwerkApp.repositories.SparePartRepository;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

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
            SparePartRepository.init();
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
