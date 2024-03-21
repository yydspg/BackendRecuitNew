package com.pg.backend.base.myHashMap.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author paul 2024/3/21
 */

public class MyHashMap<K,V> implements MyMap<K,V>{

    // default capacity
    static final int MY_DEFAULT_INITIAL_CAPACITY = 1 << 4;
    // max capacity
    static final int MY_MAXIMUM_CAPACITY = 1 << 30;
    // load factor
    static final float MY_DEFAULT_LOAD_FACTOR = 0.75f;


    static class MyNode<K,V> implements MyMap.myEntry<K,V> {
        final int hash;
        final K key;
        V value;
        MyHashMap.MyNode<K,V> next;

        MyNode(int hash, K key, V value, MyHashMap.MyNode<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final V setValue(V newValue) {
            // return oldValue
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public  final V getValue(K key) {
            return value;
        }

        @Override
        public final  K getKey() {
            return key;
        }
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

    }
    // constructor
    public MyHashMap() {
        this.loadFactor = MY_DEFAULT_LOAD_FACTOR;
    }
    public MyHashMap(int initialCapacity) {
        this(initialCapacity, MY_DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initCapacity,float loadFactor) {
        if(initCapacity < 0) {
            throw new IllegalArgumentException();
        }
        if(initCapacity > MY_MAXIMUM_CAPACITY)
            initCapacity = MY_MAXIMUM_CAPACITY;
        if(loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException();
        }
        this.loadFactor = loadFactor;

        this.threshold = tableSizeFor(initCapacity);    }
    // field

    MyNode<K,V>[] Node;

    Set<myEntry<K,V>> entrySet;

    int size;
    float loadFactor;

    //resize (capacity * load factor)
    int threshold;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey() {
        return false;
    }

    @Override
    public boolean containsValue() {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    // other
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >=MY_MAXIMUM_CAPACITY) ? MY_MAXIMUM_CAPACITY : n + 1;
    }
}
