package com.smith.sdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Smith on 2017/3/28.
 */
public class Test {
    private String[] field = new String[2];

    public Test() {
        field[0] = "hello";
        field[1] = "world";
    }

    public static void main(String[] args) {
        new Test().update();
    }

    // test ok
    private void insert() {
        Query query = new Query();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "danny");
        map.put("message", "hello world");
        map.put("dateline", 123123213);
        query.setConnection(DatabaseHelper.getConnection()).table("comment").insert(map);
    }

    private void buildDelete() {
        Builder builder = new Builder();
        builder.setTABLE_NAME("data");
        System.out.println(builder.delete(field));
    }

    private static void delete() {
        Query query = new Query();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "danny");
        query.setConnection(DatabaseHelper.getConnection()).table("comment").delete(map);
    }

    private void buildUpdate() {
        Builder builder = new Builder();
        builder.setTABLE_NAME("data");
        System.out.println(builder.update(field, field));
    }

    private void update() {
        Query query = new Query();
        Map<String, Object> where = new HashMap<>();
        where.put("id", 1);
        where.put("name", "smith");
        Map<String, Object> update = new HashMap<>();
        update.put("message", "hello");
        query.setConnection(DatabaseHelper.getConnection()).table("comment").where(where).update(update);
    }
}
