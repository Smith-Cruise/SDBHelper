package com.smith.sdb.dbpool;

/**
 * Created by Smith on 2017/4/9.
 */
public class Config {
    private String driver; // 数据库连接驱动
    private String url; // 数据库连接URL
    private String username; // 数据库连接user
    private String password; // 数据库连接password
    private int minConnection; // 数据库连接池最小连接数
    private int maxConnection; // 数据库连接池最大连接数
    private long timeoutValue; // 连接的最大空闲时间
    private long waitTime; // 取得连接的最大等待时间

    String getDriver() {
        return driver;
    }

    void setDriver(String driver) {
        this.driver = driver;
    }

    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    int getMinConnection() {
        return minConnection;
    }

    void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    int getMaxConnection() {
        return maxConnection;
    }

    void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    long getWaitTime() {
        return waitTime;
    }

    void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
