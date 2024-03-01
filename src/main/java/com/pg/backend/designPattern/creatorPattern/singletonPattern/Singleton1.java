package com.pg.backend.designPattern.creatorPattern.singletonPattern;

/*
    饿汉,类加载实例化
 * @author paul 2024/2/29
 */

import java.io.Serializable;

public class Singleton1 implements Serializable {
    private static final Singleton1 singleton1 = new Singleton1();
    private Singleton1(){}

    public static Singleton1 getSingleton1() {
        return singleton1;
    }
    /* 序列化破坏单例解决方法 ,反序列化调用此方法 ,checkResolve()*/
    public Object readResolve() {
        return singleton1;
    }
}
