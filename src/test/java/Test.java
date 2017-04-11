import com.smith.sdb.query.Query;

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
        for (int i=0; i<500; i++) {
            new Thread(()-> {
                new Test().count();
            }).start();
        }
    }

    // test ok
    public void insert() {
        Query query = new Query();
        Map<String, Object> map = new HashMap<>();
        map.put("name", Thread.currentThread().getName());
        map.put("message", "insert test");
        map.put("dateline", System.currentTimeMillis());
        try {
            query.setConnection().table("comment").insert(map);
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // test ok
    public void delete() {
        Query query = new Query();
        Map<String, Object> map = new HashMap<>();
        map.put("dateline", "1491900854776");
        try {
            query.setConnection().table("comment").where(map).delete();
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // test ok
    public void update() {
        Query query = new Query();
        Map<String, Object> where = new HashMap<>();
        where.put("id", 2);
        Map<String, Object> update = new HashMap<>();
        update.put("message", "update");
        try {
            query.setConnection().table("comment").where(where).update(update);
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // test ok
    private void select() {
        try {
            Query query = new Query();
            List<TestEntity> testList = query.setConnection().table("comment").selectList(TestEntity.class);
            for (TestEntity t: testList) {
                System.out.println(t.getName());
            }
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test ok
    public void count() {
        try {
            Query query = new Query();
            System.out.println(query.setConnection().table("comment").count());
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
