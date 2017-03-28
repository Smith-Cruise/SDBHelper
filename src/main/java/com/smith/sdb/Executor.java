package com.smith.sdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Smith on 2017/3/28.
 */
public class Executor {

    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void insert(Connection connection, String TABLE_NAME, Map<String, Object> map) throws SQLException {
        if (map.size() == 0)
            throw new SQLException("MapField is not defined");

        Object[] objects = new Object[map.size()];
        String[] fields = new String[map.size()];
        int offset = 0;
        for (String key: map.keySet()) {
            fields[offset] = key;
            objects[offset] = map.get(key);
            offset++;
        }
        Builder builder = new Builder();
        builder.setTABLE_NAME(TABLE_NAME);
        String sql = builder.insert(fields);
        execute(connection, sql, objects);
    }

    static void delete(Connection connection, String TABLE_NAME, Map<String, Object> map) throws SQLException {
        if (map.size() == 0)
            throw new SQLException("MapField is not defined");
        Object[] objects = new Object[map.size()];
        String[] fields = new String[map.size()];
        int offset = 0;
        for (String key: map.keySet()) {
            fields[offset] = key;
            objects[offset] = map.get(key);
            offset++;
        }
        Builder builder = new Builder();
        builder.setTABLE_NAME(TABLE_NAME);
        String sql = builder.delete(fields);
        execute(connection, sql, objects);
    }

    static void update(Connection connection, String TABLE_NAME, Map<String, Object> update, Map<String, Object> where) throws SQLException {
        if (update.size() == 0)
            throw new SQLException("Set MapField is not defined");
        if (where.size() ==0)
            throw new SQLException("Update MapField is not defined");
        Object[] objects = new Object[update.size()+where.size()];
        String[] setFields = new String[update.size()];
        String[] updateFields = new String[where.size()];
        int objOffset = 0;
        int offset = 0;
        for (String key: update.keySet()) {
            setFields[offset] = key;
            objects[objOffset] = update.get(key);
            offset++;
            objOffset++;
        }

        offset = 0;
        for (String key: where.keySet()) {
            updateFields[offset] = key;
            objects[objOffset] = where.get(key);
            offset++;
            objOffset++;
        }
        Builder builder = new Builder();
        builder.setTABLE_NAME(TABLE_NAME);
        String sql = builder.update(setFields, updateFields);
        execute(connection, sql, objects);
    }

    private static void execute(Connection connection, String sql, Object[] objects) throws SQLException {
        if (connection == null)
            throw new SQLException("connection is null");
        if (sql == null)
            throw new SQLException("sql is null");

        PreparedStatement ps = connection.prepareStatement(sql);
        if (objects.length != 0) {
            for (int i=0; i<objects.length; i++) {
                ps.setObject(i+1, objects[i]);
            }
        }

        ps.execute();
    }
}
