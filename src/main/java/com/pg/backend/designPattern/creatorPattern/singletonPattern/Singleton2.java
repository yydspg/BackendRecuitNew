package com.pg.backend.designPattern.creatorPattern.singletonPattern;

/*
    懒汉-,加单锁,性能问题,读操作需获取锁才可以
 * @author paul 2024/2/29
 */

public class Singleton2 {
    /* 此时singleton2还没指向 ,无法设置为 final */
    private static Singleton2 singleton2 = null;

    private  Singleton2(){

    }

    private static synchronized Singleton2 getSingleton2() {
        if (singleton2 == null) {
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}
