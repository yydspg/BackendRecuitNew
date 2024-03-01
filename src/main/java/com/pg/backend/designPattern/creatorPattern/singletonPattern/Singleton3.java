package com.pg.backend.designPattern.creatorPattern.singletonPattern;

/**
 * 懒汉-双检
 * @author paul 2024/2/29
 */

public class Singleton3 {
    /* volatile 防止 jvm 优化和指令重排 */
    private static volatile Singleton3 singleton3;
    private static boolean flag = false;
    private Singleton3() {
        /* 反射解决方法 */
        synchronized (Singleton3.class) {
            if (flag) {
                throw new RuntimeException("不可创建多个对象");
            }
            flag = true;
        }
    }

    public static Singleton3 getInstance() {
        /* 不为null,直接返回,无需获取锁 */
        if (singleton3 == null) {
            synchronized (Singleton3.class) {
                if (singleton3 == null) {
                    singleton3 = new Singleton3();
                }
            }
        }
        return singleton3;
    }
}
