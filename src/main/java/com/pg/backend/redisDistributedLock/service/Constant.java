package com.pg.backend.redisDistributedLock.service;

/**
 * @author paul 2024/2/29
 */

public enum Constant {
    TE("order100"),
    LOCK("redisCommonLock");
    private String des;
    private Constant(String des) {
        this.des = des;
    }
    public String getDes(){
        return des;
    }
}
