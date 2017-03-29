import com.smith.sdb.Builder;
import com.smith.sdb.Query;

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
        new Test().count();
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

    private static void delete() {
        Query query = new Query();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "danny");
        query.setConnection(DatabaseHelper.getConnection()).table("comment").where(map).delete();
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

    private void select() {
        Query query = new Query();
        List<TestEntity> testList = query.setConnection(DatabaseHelper.getConnection()).table("comment").selectList(TestEntity.class);
        for (TestEntity t: testList) {
            System.out.println(t.getName());
        }
    }

    private void count() {
        Query query = new Query();
        System.out.println(query.setConnection(DatabaseHelper.getConnection()).table("comment").count());
    }
}
