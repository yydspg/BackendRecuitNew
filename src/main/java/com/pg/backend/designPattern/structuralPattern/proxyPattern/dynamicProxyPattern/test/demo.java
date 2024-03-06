package com.pg.backend.designPattern.structuralPattern.proxyPattern.dynamicProxyPattern.test;


import com.pg.backend.designPattern.structuralPattern.proxyPattern.dynamicProxyPattern.main.MyDynamicProxyFactory;
import com.pg.backend.designPattern.structuralPattern.proxyPattern.dynamicProxyPattern.main.MyInterface;
import com.pg.backend.designPattern.structuralPattern.proxyPattern.dynamicProxyPattern.main.MyProxy;
import com.pg.backend.designPattern.structuralPattern.proxyPattern.staticProxyPattern.entity.MyRealSubject;
import com.pg.backend.designPattern.structuralPattern.proxyPattern.staticProxyPattern.entity.MySubject;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.Proxy;

/**
 * @author paul 2024/3/6
 */
@Slf4j
public class demo {
    public static void main(String[] args) throws NoSuchMethodException {

        MyDynamicProxyFactory myDynamicProxyFactory = new MyDynamicProxyFactory();
        myDynamicProxyFactory.put(MyProxy.getInstance());
        MyInterface proxy = (MyInterface) myDynamicProxyFactory.getProxy(MyProxy.class.getName());
        proxy.test();
    }
}
