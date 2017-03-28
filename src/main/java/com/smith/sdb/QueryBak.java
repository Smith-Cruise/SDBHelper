package com.smith.sdb;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Smith on 2017/3/26.
 */
public class QueryBak {
    private Connection connection;

    private String table;

    private String primaryKeyName;

    private Map<String, Object> updateField;

    private Map<String, Object> insertField;

    private Map<String, Object> deleteField;

    private Map<String, Object> queryField;

    private Map<String, Object> whereField;

    void setConnection(String DRIVER, String URL, String USERNAME, String PASSWORD) {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void setTable(String table) {
        this.table = table;
    }

    void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    void setWhere(Map<String, Object> fieldMap) {
        this.whereField = fieldMap;
    }

    boolean insert(Map<String, Object> fieldMap) {
        try {
            checkBase();
            this.insertField = fieldMap;
            int length = insertField.size();
            int offset = 0;
            if (length <= 0)
                throw new SQLException("Insert data is not defined");

            Object[] objects = new Object[length];
            StringBuffer sql = new StringBuffer("INSERT INTO `"+table+"` (");
            for (String key: insertField.keySet()) {
                sql.append("`");
                sql.append(key);
                sql.append("`,");
                objects[offset] = insertField.get(key);
                offset++;
            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(") VALUES (");
            for (int i=0; i<length; i++) {
                sql.append("?,");
            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(")");
            doExecuteInsert(sql.toString(), objects);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }

    boolean delete() {
        try {
            checkBase();
            checkWhere();
            this.deleteField = whereField;

            int length = deleteField.size();
            int offset = 0;
            if (length <= 0)
                throw new SQLException("Delete data is not defined");

            Object[] objects = new Object[length];
            StringBuffer sql = new StringBuffer("DELETE FROM `"+table+"` WHERE ");
            for (String key: deleteField.keySet()) {
                sql.append("`"+key+"`=? AND");
                objects[offset] = deleteField.get(key);
                offset++;
            }
            sql.delete(sql.length()-4, sql.length());
            doExecuteDelete(sql.toString(), objects);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }

    <T> T queryEntity(Class<T> c) {
        try {
            checkBase();
            checkWhere();
            this.queryField = this.whereField;
            if (queryField.size() <= 0)
                throw new SQLException("Query data is not defined");

            String sql = buildQuerySql(queryField);
            Object[] objects = buildObjects(queryField);
            ResultSet rs = doExecuteQuery(sql, objects);
            return AutoLoader.loadEntity(rs, c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    // todo 得到Map格式的数据
    <T> List<T> queryList(Class<T> c) {
        try {
            checkBase();
            this.queryField = this.whereField;
            if (queryField.size() == 0) {
                String sql = "SELECT * FROM `"+table+"`";
                Object[] objects = new Object[0];
                ResultSet rs = doExecuteQuery(sql, objects);
                return checkList(AutoLoader.loadList(rs,c));
            } else {
                if (queryField.size() <= 0)
                    throw new SQLException("Query data is not defined");

                String sql = buildQuerySql(queryField);
                Object[] objects = buildObjects(queryField);
                ResultSet rs = doExecuteQuery(sql, objects);
                return checkList(AutoLoader.loadList(rs, c));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    private void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String buildQuerySql(Map<String, Object> fieldMap) {
        int length = fieldMap.size();
        int offset = 0;
        Object[] objects = new Object[length];
        StringBuffer sql = new StringBuffer("SELECT * FROM `");
        sql.append(table);
        sql.append("` WHERE ");
        for (String key: fieldMap.keySet()) {
            sql.append("`");
            sql.append(key);
            sql.append("`=? AND ");
            objects[offset] = fieldMap.get(key);
            offset++;
        }
        sql.delete(sql.length()-4, sql.length());
        return sql.toString();
    }

    private Object[] buildObjects(Map<String, Object> fieldMap) {
        int length = fieldMap.size();
        int offset = 0;
        Object[] objects = new Object[length];
        for (String key: fieldMap.keySet()) {
            objects[offset] = fieldMap.get(key);
            offset++;
        }
        return objects;
    }

    private void checkBase() throws SQLException {
        if (connection == null)
            throw new SQLException("connection is not defined");
        if (table == null)
            throw new SQLException("table is not defined");
    }

    private void checkWhere() throws SQLException {
        if (whereField == null)
            throw new SQLException("Where data is not defined");
    }

    private <T> List<T> checkList(List<T> list) {
        if (list.size() == 0)
            return null;
        else
            return list;
    }

    private void doExecuteInsert(String sql, Object[] params) throws SQLException {
        doExecute(sql, params);
    }

    private void doExecuteDelete(String sql, Object[] params) throws SQLException {
        doExecute(sql, params);
    }

    private ResultSet doExecuteQuery(String sql, Object[] params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs;
        int length = params.length;
        if (length > 0) {
            for (int i=1; i<=length; i++) {
                ps.setObject(i, params[i-1]);
            }
            rs = ps.executeQuery();
        } else {
            rs = ps.executeQuery();
        }
        return rs;
    }

    private void doExecute(String sql, Object[] params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        int length = params.length;
        if (length > 0) {
            for (int i=1; i<=length; i++) {
                ps.setObject(i, params[i-1]);
            }
            ps.execute();
        } else {
            ps.execute();
        }
    }
}
