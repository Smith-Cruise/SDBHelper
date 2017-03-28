package com.smith.sdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Smith on 2017/3/28.
 */
public class Query {
    private Connection connection;
    private String tableName;

    private Map<String, Object> whereMap;

    // todo this function is waiting to build
    private String[] field;

    private int limit = 5;

    private int page = 1;

    private String order;

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

    public Query field(String[] s) {
        this.field = s;
        return this;
    }

    public Query limit(int a) {
        this.limit = a;
        return this;
    }

    public Query page(int a) {
        this.page = a;
        return this;
    }

    public Query order(String s) {
        this.order = s;
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

    public <T> List<T> selectList(Class<T> c) {
        try {
            ResultSet rs = Executor.select(connection, tableName, field, whereMap, order, buildLimit());
            return AutoLoader.loadList(rs, c);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            Executor.close(connection);
        }
    }

    public <T> T select(Class<T> c) {
        try {
            ResultSet rs = Executor.select(connection, tableName, field, whereMap, order, buildLimit());
            return AutoLoader.loadEntity(rs, c);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            Executor.close(connection);
        }
    }

    private String buildLimit() {
        return (page-1)*limit+","+limit;
    }
}
