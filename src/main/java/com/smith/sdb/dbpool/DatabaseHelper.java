package com.smith.sdb.dbpool;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Smith on 2017/4/10.
 */
public class DatabaseHelper {
    private static final String CONFIG_FILE = "SDBHelper.properties";

    private static Pool pool = null;

    public synchronized static Connection getConnection() throws Exception {
        Properties properties = readProps();
        if (properties == null)
            throw new Exception("configure file(SDBHelper.properties) is not found");

        Config config = new Config();
        config.setDriver(properties.getProperty("driver"));
        config.setUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.setMaxConnection(Integer.valueOf(properties.getProperty("maxConnection")));
        config.setMinConnection(Integer.valueOf(properties.getProperty("minConnection")));
        config.setWaitTime(Integer.valueOf(properties.getProperty("waitTime")));
        if (pool == null) {
            pool = new Pool(config);
        }

        return pool.getConnection();
    }

    public static void returnConnection(Connection connection) {
        if (pool == null)
            return;
        pool.returnConnection(connection);
    }

    private static Properties readProps() {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return properties;
    }
}
