package com.smith.sdb;

import java.sql.Connection;

/**
 * Created by Smith on 2017/3/28.
 */
public abstract class BaseConfig {
    String TABLE_NAME = null;

    void setTABLE_NAME(String s) {
        this.TABLE_NAME = s;
    }

}
