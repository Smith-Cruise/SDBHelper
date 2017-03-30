package com.smith.sdb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Smith on 2017/3/27.
 */
public class AutoLoader {

    static <E> List<E> loadList(ResultSet rs, Class<E> c) {
        LinkedList<E> list = new LinkedList<E>();
        int length = 0;
        Field[] fields = c.getDeclaredFields();
        try {
            while (rs.next()) {
                E objectCopy = getObject(rs,fields,c);
                list.add(objectCopy);
                length++;
            }
        }  catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (length ==0)
            return null;
        else
            return list;
    }

    static <E> E loadEntity(ResultSet rs,Class<E> c) {
        Field[] fields = c.getDeclaredFields();
        E entity = null;
        try {
            while (rs.next()) {
                entity = getObject(rs, fields, c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    private static <E> E getObject(ResultSet rs, Field[] fields,Class<E> c) {
        E obj = null;
        try {
            obj = c.newInstance();
            for (Field field: fields) {
                Object value = null;
                String fieldName = field.getName();
                /* int */
                if (field.getType().getName().equals(int.class.getName())) {
                    value = rs.getInt(fieldName);
                }

                /* double */
                if (field.getType().getName().equals(double.class.getName())) {
                    value = rs.getDouble(fieldName);
                }

                /* String */
                if (field.getType().getName().equals(String.class.getName())) {
                    value = rs.getString(fieldName);
                }

                /* long */
                if (field.getType().getName().equals(long.class.getName())) {
                    value = rs.getLong(fieldName);
                }

                /* 方法反射 */
                String first = fieldName.substring(0,1).toUpperCase();
                String newFieldName = first+fieldName.substring(1);
                String methodName = "set"+newFieldName;

                Method method = c.getMethod(methodName,field.getType());
                method.invoke(obj,value);
            }
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
}
