package com.smith.sdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Smith on 2017/3/28.
 */
public class Query {
    private Connection connection;
    private String tableName;

    private Map<String, Object> whereMap;

    public Query setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Query table(String table) {
        tableName = table;
        return this;
    }

    public Query where(Map<String, Object> map) {
        this.whereMap = map;
        return this;
    }

    public boolean insert(Map<String, Object> map) {
        try {
            Executor.insert(connection, tableName, map);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Executor.close(connection);
        }

    }

    public boolean delete(Map<String, Object> map) {
        try {
            Executor.delete(connection, tableName, map);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Executor.close(connection);
        }
    }

    public boolean update(Map<String, Object> map) {
        try {
            if (whereMap == null)
                throw new SQLException("where condition is not defined");
            Executor.update(connection, tableName, map, whereMap);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Executor.close(connection);
        }
    }
}
