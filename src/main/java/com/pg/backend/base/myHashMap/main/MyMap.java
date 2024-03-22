package com.pg.backend.base.myHashMap.main;

import java.util.AbstractMap;

/**
 * @author paul 2024/3/6
 */

public interface MyMap<K,V> {

    int size();

    boolean isEmpty();


    V get(K key);

    V put(K key,V value);

    V remove(K key);


    interface myEntry<K, V>  {
        boolean equals(Object o);
        V setValue(V value);
        V getValue(K key);
        K getKey();
        int hashCode();
    }

}
