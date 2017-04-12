# SDBHelper 使用手册
> 此程序只是个人练习项目，仅供学习参考，切勿应用在实际项目（虽然我是在用的）
### SDBHelper是一个自带数据库连接池的JAVA数据库辅助软件，语法参考了Thinkphp的链式操作（如果下面教程不清晰，你们可以直接参考thinkphp的数据库操作）
***
*注意：程序暂时只支持Mysql*
###### 贴士
程序你可以自行选择Connection连接的获取方式，你可以自己写程序获取，获取使用自带的数据库连接池。在`test/java/Test.java`中有我的代码示例
###### 重点
数据库操作完毕后，请务必关闭数据连接，如果是自己提供的Connection的连接，你可以自己关闭，或者调用我提供的`close()`方法，详情可见源码。如果是使用我们的数据库连接池，那就必须要调用`close()`方法，否则数据库连接池将很快被耗尽
###### 自行导入jar包
jar包在`out/artifacts/SDBHelper.jar`
###### 获取Connection的两种方式
*使用自己生成的Connection*
```java
// DatabaseHelper为你的获取数据库的类。
Query query = new Query();
query.setConnection(DatabaseHelper.getConnection());
```
*使用我们的数据库连接池，但是你要在Classpath目录里放`SDBHelper.properties`的配置文件*
```xml
// SDBHelper.properties 的格式范例
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC
username=root
password=
minConnection=3
maxConnection=10
waitTime=300
```
```java
Query query = new Query();
query.setConnection(); // 不需要带参数即可
```
###### 插入数据
*以Map格式放置数据，Key名称必须与数据库字段名相对应*
```java
public void insert() {
        Query query = new Query();
        Map<String, Object> map = new HashMap<>();
        map.put("name", Thread.currentThread().getName());
        map.put("message", "insert test");
        map.put("dateline", System.currentTimeMillis());
        try {
            query
                    .setConnection()
                    .table("comment")
                    .insert(map);
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###### 删除数据
```java
public void delete() {
        Query query = new Query();
        // 指定删除条件
        Map<String, Object> map = new HashMap<>();
        map.put("dateline", "1491900854776");
        try {
            query
                    .setConnection()
                    .table("comment")
                    .where(map)
                    .delete();
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###### 更新数据
```java
public void update() {
        Query query = new Query();
        // 指定where的条件
        Map<String, Object> where = new HashMap<>();
        where.put("id", 2);
        // 指定update的字段
        Map<String, Object> update = new HashMap<>();
        update.put("message", "update");
        try {
            query
                    .setConnection()
                    .table("comment")
                    .where(where)
                    .update(update);
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###### 查询数据
> 你可以选择获得List集合，也可以选择获取单单的实体，也可以指定where条件，自己探索吧
```java
public void select() {
        try {
            Query query = new Query();
            List<TestEntity> testList = query
                    .setConnection()
                    .table("comment")
                    .selectList(TestEntity.class);
            for (TestEntity t: testList) {
                System.out.println(t.getName());
            }
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
###### 杂项
```java
public void count() {
        try {
            Query query = new Query();
            System.out.println(query.setConnection().table("comment").count());
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```