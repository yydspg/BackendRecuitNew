package com.pg.backend.designPattern.creatorPattern.singletonPattern;

/**
 * 懒汉-静态内部类
 * jvm 加载外部类不会加载静态内部类,static修饰jvm保证只会架加载一次
 * @author paul 2024/2/29
 */

public class Singleton4 {
    private Singleton4() {}
    /* 静态内部类和其自身不存在依赖关系 */
    private static class SingletonHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }
    public static Singleton4 getInstance () {
        return SingletonHolder.INSTANCE;
    }
}
