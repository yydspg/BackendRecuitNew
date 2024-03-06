package com.pg.backend.designPattern.structuralPattern.proxyPattern.dynamicProxyPattern.main;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author paul 2024/3/6
 */
@Slf4j
public class MyProxy implements InvocationHandler,MyInterface {
    private static volatile MyProxy myProxy;

    private static boolean flag = false;

    private MyProxy() {
        if(flag) {
           throw new RuntimeException("canNotCreateMoreInstance");
        }
        flag  = true;
    }
    public static MyProxy getInstance() {
        if(myProxy == null) {
            synchronized (MyProxy.class) {
                if(myProxy == null){
                    myProxy = new MyProxy();
                }
            }
        }
        return myProxy;
    }
    public void test(){
        log.info("test...");
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("beforeProxy");
        return method.invoke(MyProxy.getInstance(),args);
    }
}
