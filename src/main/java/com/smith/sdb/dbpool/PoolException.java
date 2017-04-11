package com.smith.sdb.dbpool;

/**
 * Created by Smith on 2017/4/9.
 */
public class PoolException extends Exception {
    PoolException() {
        super("Pool Error");
    }

    PoolException(String msg) {
        super(msg);
    }
}
