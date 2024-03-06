package com.pg.backend.base.myHashMap.main;

/**
 * @author paul 2024/3/6
 */

public interface MyMap<K,V> {

    int size();

    boolean isEmpty();

    boolean containsKey();

    boolean containsValue();

    V get(Object key);

    V put(K key,V value);

    V remove(Object key);
}
