package com.smith.sdb;

/**
 * Created by Smith on 2017/3/28.
 */
public class Builder extends BaseConfig {
    private String insertSql = "INSERT INTO `%TABLE%` (%FIELD%) VALUES (%DATA%)";
    private String deleteSql = "DELETE FROM `%TABLE%` %WHERE%";
    private String updateSql = "UPDATE `%TABLE%` SET %SET% %WHERE%";
    private String selectSql = "SELECT %FIELD% FROM `%TABLE%` %WHERE% %ORDER% %LIMIT%";

    String insert(String[] fields) {
        String sql = insertSql;
        StringBuilder stringBuilderField = new StringBuilder();
        StringBuilder stringBuilderData = new StringBuilder("");
        for (String s: fields) {
            // 增加field参数
            stringBuilderField.append('`');
            stringBuilderField.append(s);
            stringBuilderField.append("`,");

            // 增加?参数
            stringBuilderData.append("?,");
        }

        stringBuilderField.deleteCharAt(stringBuilderField.length()-1);
        stringBuilderData.deleteCharAt(stringBuilderData.length()-1);

        sql = sql.replace("%TABLE%",TABLE_NAME);
        sql = sql.replace("%FIELD%", stringBuilderField.toString());
        sql = sql.replace("%DATA%", stringBuilderData.toString());
        return sql;
    }

    String delete(String[] fields) {
        String sql = deleteSql;
        StringBuilder whereCondition = new StringBuilder("WHERE ");
        for (String s: fields) {
            whereCondition.append("`"+s+"`=? AND ");
        }
        whereCondition.delete(whereCondition.length()-4, whereCondition.length());
        sql = sql.replace("%TABLE%",TABLE_NAME);
        sql = sql.replace("%WHERE%", whereCondition.toString());
        return sql;
    }

    String update(String[] updateFields, String[] whereFields) {
        String sql = updateSql;
        StringBuilder updateCondition = new StringBuilder();
        for (String s: updateFields) {
            updateCondition.append("`"+s+"`=?,");
        }
        updateCondition.deleteCharAt(updateCondition.length()-1);

        StringBuilder whereCondition = new StringBuilder("WHERE ");
        for (String s: whereFields) {
            whereCondition.append("`"+s+"`=? AND ");
        }
        whereCondition.delete(whereCondition.length()-4, whereCondition.length());
        sql = sql.replace("%TABLE%", TABLE_NAME);
        sql = sql.replace("%SET%", updateCondition.toString());
        sql = sql.replace("%WHERE%", whereCondition.toString());
        return sql;
    }
}
