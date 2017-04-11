package com.smith.sdb.query;

import com.smith.sdb.dbpool.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Smith on 2017/3/28.
 */
public class Query {
    private boolean isOwnConnection = false;

    private Connection connection;
    private String tableName;

    private Map<String, Object> whereMap;

    private String[] field;

    private int limit = 5;

    private int page = 1;

    private String order;

    public Query setConnection(Connection connection) {
        isOwnConnection = true;
        this.connection = connection;
        return this;
    }

    public Query setConnection() throws Exception {
        this.connection = DatabaseHelper.getConnection();
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

    public void insert(Map<String, Object> map) throws Exception {
        Executor.insert(connection, tableName, map);
    }

    public void delete() throws Exception {
        if (whereMap == null)
            throw new Exception("where condition is not defined");
        Executor.delete(connection, tableName, whereMap);
    }

    public void update(Map<String, Object> map) throws Exception {
        if (whereMap == null)
            throw new SQLException("where condition is not defined");
        Executor.update(connection, tableName, map, whereMap);
    }

    public <T> List<T> selectList(Class<T> c) throws Exception {
        ResultSet rs = Executor.select(connection, tableName, field, whereMap, order, buildLimit());
        return AutoLoader.loadList(rs, c);
    }

    public <T> T select(Class<T> c) throws Exception {
        ResultSet rs = Executor.select(connection, tableName, field, whereMap, order, buildLimit());
        return AutoLoader.loadEntity(rs, c);
    }

    public int count() throws Exception {
        return Executor.count(connection, tableName, whereMap);
    }

    public void close() throws Exception {
        if (isOwnConnection)
            connection.close();
        else
            DatabaseHelper.returnConnection(connection);
    }

    private String buildLimit() {
        return (page-1)*limit+","+limit;
    }
}
