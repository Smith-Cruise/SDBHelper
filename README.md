# SDBHelper Guide
### NOTICE | 注意
    You should create and close mysql connection by yourself.
    For example,I create a class `DatabaseHelper` for me to get connection
    And also create `TestEntity`
    You can see these code in the end of page 

    Mysql的Connection需要由你自己创建获取，关闭connection也要自己关闭
    例如，我创建了一个类`DatabaseHelper`来获取connection连接
    还有一个实体`TestEntity`,后面有示例代码
### USAGE | 用法
###### insert | 增加数据
```java
Query query = new Query();
Map<String, Object> map = new HashMap<>();
// NOTICE:Your map fields should be same as your database fields
// 注意：你的Map 键值需要和数据库字段名相对应
map.put("name", "danny");
map.put("message", "hello world");
map.put("dateline", 123123213);    

boolean result = query
.setConnection(DatabaseHelper.getConnection())
.table("comment")
.insert(map);
```
###### delete | 删除数据
```java
Query query = new Query();
// this is a condition
// 指定删除条件
Map<String, Object> map = new HashMap<>();
map.put("name", "danny");


boolean result = query
.setConnection(DatabaseHelper.getConnection())
.table("comment")
.where(map)
.delete();
```
###### update  | 更新
```java
Query query = new Query();
Map<String, Object> where = new HashMap<>();
where.put("id", 1);
where.put("name", "smith");
Map<String, Object> update = new HashMap<>();
update.put("message", "hello");


boolean result = query
.setConnection(DatabaseHelper.getConnection())
.table("comment")
.where(where)
.update(update);
```
###### query
```
// basic
Query query = new Query();
List<TestEntity> testList = query
.setConnection(DatabaseHelper.getConnection())
.table("comment")
.selectList(TestEntity.class);

// advanced
Query query = new Query();
List<TestEntity>testList = query
.setConnection(DatabaseHelper.getConnection())
.table("comment")
.where(condition)
.order("dateline desc")
.selectList(TestEntity.class);

// other
Query query = new Query();
TestEntity entity = query
.setConnection(DatabaseHelper.getConnection())
.table("comment")
.where(condition)
.order("dateline desc")
.select(TestEntity.class);
```
## Example Code | 示例代码
###### DatabaseHelper.java
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        DRIVER = "com.mysql.cj.jdbc.Driver";
        URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
        USERNAME = "root";
        PASSWORD = "";

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("can't find jdbc driver");
        }
    }

    static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
    
    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```
###### TestEntity
```java
public class TestEntity {
    private int id;
    private String name;
    private String message;
    private long dateline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
        this.dateline = dateline;
    }
}
```