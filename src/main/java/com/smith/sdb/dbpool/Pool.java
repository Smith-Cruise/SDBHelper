package com.smith.sdb.dbpool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Smith on 2017/4/9.
 */
class Pool {
    private Config config = null;

    private Vector<PooledConnection> connections = null;

    Pool(Config config) {
        this.config = config;
    }

    synchronized Connection getConnection() throws Exception {
        Connection connection = null;

        if (connections == null) {
            createPool();
        }

        connection = getFreeConnection();
        while (connection == null) {
            wait(config.getWaitTime());
            connection = getFreeConnection();
        }
        return connection;
    }

    /**
     *  返回数据库连接
     */
    synchronized void returnConnection(Connection con) {
        if (connections == null)
            return;

        Iterator<PooledConnection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            PooledConnection temp = iterator.next();
            if (con == temp.getConnection()) {
                temp.setBusy(false);
                return;
            }
        }
    }

    private void createPool() throws Exception {
        if (config == null) {
            throw new PoolException("configure is not found");
        }

        if (connections != null)
            return;

        Class.forName(config.getDriver());
        connections = new Vector<>();
        initConnection();
    }

    private void initConnection() throws SQLException {
        for (int i=1; i<=config.getMinConnection(); i++) {
            Connection connection = createConnection();
            connections.addElement(new PooledConnection(connection));

            // 第一次获取connection 自动调整MAXConnection
            if (i == 1) {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                int maxConnections = databaseMetaData.getMaxConnections();
                if (maxConnections>0 && this.config.getMaxConnection()>maxConnections) {
                    this.config.setMaxConnection(maxConnections);
                }
            }
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    }

    private Connection getFreeConnection() {
        if (connections.size() >= config.getMaxConnection()) {
            Iterator<PooledConnection> iterator = connections.iterator();
            while (iterator.hasNext()) {
                PooledConnection pooledConnection = iterator.next();
                if (!pooledConnection.isBusy()) {
                    pooledConnection.setBusy(true);
                    Connection connection = pooledConnection.getConnection();
                    if (testConnection(connection)) {
                        // connection is valid, return it
                        return pooledConnection.getConnection();
                    } else {
                        // connection is invalid, get connection again
                        iterator.remove();
                    }
                }
            }
            return null;
        } else {
            try {
                Connection connection = createConnection();
                connections.addElement(new PooledConnection(connection));
                return connection;
            } catch (SQLException e) {
                return null;
            }
        }
    }

    private boolean testConnection(Connection connection) {
        boolean status;

        try {
            status = connection.isValid(0);
        } catch (SQLException e) {
            status = false;
        }
        return status;
    }

    private void wait(int microSecond) throws Exception {
        Thread.sleep(microSecond);
    }
}
