# SDBHelper Guide
## English Guide
###### NOTICE
    You should create mysql connection by yourself.
    For example,I create a class `DatabaseHelper` for me to get connection
###### USAGE
```
Query query = new Query();
query.setConnection(DatabaseHelp.getConnection()).table("table_name").select();
```
## Chinese Guide
###### 注意
    Mysql的Connection需要由你自己创建获取
    例如，我创建了一个类`DatabaseHelper`来获取connection连接
###### USAGE
```
Query query = new Query();
query.setConnection(DatabaseHelp.getConnection()).table("table_name").select();
```
