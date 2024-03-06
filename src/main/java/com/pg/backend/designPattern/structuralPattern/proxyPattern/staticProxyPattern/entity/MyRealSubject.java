package com.pg.backend.designPattern.structuralPattern.proxyPattern.staticProxyPattern.entity;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 真实主题类,实现抽象主题类中的具体业务方法
 * @author paul 2024/3/6
 */
@Slf4j
public class MyRealSubject implements MySubject, InvocationHandler {
    @Override
    public void execute() {
        log.error("[{}]","testProxyMode");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("proxyTest");
        Object invoke = method.invoke(MyRealSubject.this, args);
        return invoke;
    }
}
