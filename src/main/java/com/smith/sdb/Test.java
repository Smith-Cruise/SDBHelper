package com.smith.sdb;

import java.util.HashMap;
import java.util.List;
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
        new Test().select();
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

    private void buildSelect() {
        Builder builder = new Builder();
        builder.setTABLE_NAME("data");
        System.out.println(builder.select(field, field, "name desc", "2,5"));
        System.out.println(builder.select(null, field, null, null));
    }

    private void select() {
        Query query = new Query();
        List<TestEntity> testList = query.setConnection(DatabaseHelper.getConnection()).table("comment").selectList(TestEntity.class);
        for (TestEntity t: testList) {
            System.out.println(t.getName());
        }
    }
}
