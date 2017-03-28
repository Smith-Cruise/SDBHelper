package com.smith.sdb;

import java.util.List;
import java.util.Map;

/**
 * Created by Smith on 2017/3/27.
 */
public class SDBHelperBAK {
    private QueryBak query;

    public SDBHelperBAK() {
        query = new QueryBak();
    }

    public SDBHelperBAK setConnection(String DRIVER, String URL, String USERNAME, String PASSWORD) {
        query.setConnection(DRIVER, URL, USERNAME, PASSWORD);
        return this;
    }

    public SDBHelperBAK setConnection(Map<String, String> fieldMap) {
        query.setConnection(fieldMap.get("DRIVER"), fieldMap.get("URL"), fieldMap.get("USERNAME"), fieldMap.get("PASSWORD"));
        return this;
    }

    public SDBHelperBAK setTable(String table) {
        query.setTable(table);
        return this;
    }

    public SDBHelperBAK update() {
        return this;
    }

    public SDBHelperBAK where(Map<String, Object> fieldMap) {
        query.setWhere(fieldMap);
        return this;
    }

    public boolean delete() {
        return query.delete();
    }

    public boolean insert(Map<String, Object> fieldMap) {
        return query.insert(fieldMap);
    }

    public <T> List<T> queryList(Class<T> c) {
        return query.queryList(c);
    }

    public <T> T queryEntity(Class<T> c) {
        return query.queryEntity(c);
    }
}
