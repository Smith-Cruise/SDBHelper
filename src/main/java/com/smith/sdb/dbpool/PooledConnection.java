package com.smith.sdb.dbpool;

import java.sql.Connection;

/**
 * Created by Smith on 2017/4/9.
 */
public class PooledConnection {
    private Connection connection = null;

    private boolean isBusy = false;

    PooledConnection(Connection connection) {
        this.connection = connection;
    }

    Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    boolean isBusy() {
        return isBusy;
    }

    void setBusy(boolean busy) {
        isBusy = busy;
    }
}
