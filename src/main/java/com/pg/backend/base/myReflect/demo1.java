package com.pg.backend.base.myReflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author paul 2024/4/3
 */

public class demo1 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        entity entity = new entity();
        Method method = entity.class.getDeclaredMethod("getAge");
        method.invoke(entity);
    }
}
