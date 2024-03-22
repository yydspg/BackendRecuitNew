package com.pg.backend.base.myHashMap.main;

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


    // field

    MyNode<K,V>[] table;

    Set<myEntry<K,V>> entrySet;
    // real size
    int size;

    final float loadFactor;

    //resize (capacity * load factor)
    int threshold;


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

        this.threshold = tableSizeFor(initCapacity);
    }




    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }



    @Override
    public V get(K key) {
       MyNode<K,V> e;
        return (e = this.getNode(key)) == null ? null : e.value;
    }

    @Override
    public V put(K key, V value) {
        return this.putValue(hash(key), key, value, false, true);
    }

    @Override
    public V remove(K key) {
        return this.removeNode(hash(key), key,null).getValue(key);
    }

    // other
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >=MY_MAXIMUM_CAPACITY) ? MY_MAXIMUM_CAPACITY : n + 1;
    }

    private V putValue(int hash,K key ,V value,boolean ifAbsent,boolean evict) {
        // tem var
        MyNode<K,V>[] tab;
        // which one hashNode found
        MyNode<K,V> p;
        int len,i;

        // init hashMap
        if((tab = table) == null || (len = tab.length) == 0)
            len = (tab = this.resize()).length;
          /*
             array, easy mode
              */
        if((p = tab[i = ((len - 1) & hash)]) == null)
            tab[i] = this.newNode(hash,key,value,null);
        // hash collision
        else {
            MyNode<K,V> e; K k = p.key;

            if(p.hash == hash && (Objects.equals(key, k)))
                e = p;
            // TODO 2024/3/22 : 缺少红黑树
            /*
            linkedList,if Hash collision
             */
            else {
                for (int j = 0; ; j++) {
                    //Traverse to the end
                    if((e = p.next) == null ){
                        p.next = this.newNode(hash,key,value,null);
                        // TODO 2024/3/22 : 红黑树
                        break;
                    }
                    k = e.key;
                    // same hashNode  of linkedList when hash collision
                    if(e.hash == hash && Objects.equals(k,key)) break;
                    // Backward coverage
                    p = e;
                }
            }
            if(e != null) {
                V oldValue = e.value;
                //cover
                if(!ifAbsent || oldValue == null)
                    e.value = value;
                return oldValue;
            }
        }
        if (++size > threshold)
            resize();
        return null;
    }
    private MyNode<K,V>[] resize() {
        // tem var
        MyNode<K,V> [] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap , newThr = 0;

        // resize

        if(oldCap > 0) {
            // max capacity
            if(oldCap >= MY_MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return  oldTab;
            // bigger than 16(default capacity)
            } else if((newCap = oldCap << 1) < MY_MAXIMUM_CAPACITY && oldCap >= MY_DEFAULT_INITIAL_CAPACITY) {
                newCap = oldCap << 1;
            }
            // change new Capacity by old threshold
        } else if(oldThr > 0) {
            newCap = oldThr;
            // initial hashMap
        } else {
            newCap = MY_DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (MY_DEFAULT_LOAD_FACTOR * MY_DEFAULT_INITIAL_CAPACITY);
        }

        // not initial
        if(newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MY_MAXIMUM_CAPACITY && ft < (float)MY_MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
        }

        // synchronized
        threshold = newThr;
        MyNode<K,V>[] newTab = new MyNode[newCap];
        table = newTab;

        if(oldTab != null) {
            for (int j = 0; j < oldCap; j++) {
                MyNode<K,V> t;
                if((t = oldTab[j]) != null) {
                    oldTab[j] = null;

                    /*
                      array, current Node only has one element
                      */
                    if (t.next == null)
                        //  rehash
                        newTab[t.hash & (newCap - 1)] = t;
                    // TODO 2024/3/22 : 缺少红黑树
                    /*
                    linked List,if Hash collision
                     */
                    else{
                        // low part
                        MyNode<K,V> loHead = null, loTail = null;
                        //high part
                        MyNode<K,V> hiHead = null, hiTail = null;
                        // rehash
                        do {
                            if((t.hash&oldCap) == 0){
                                // first insert, loHead and loTail are same
                                if(loTail == null)
                                    loHead = t;
                                else
                                    loTail.next = t;
                                loTail = t;

                            } else {
                                // first insert,hiHead and hiTail are same
                                if (hiTail == null)
                                    hiHead = t;
                                else
                                    hiTail.next = t;
                                hiTail = t;
                            }
                        } while( (t = t.next) != null);

                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    private MyNode<K,V> removeNode(int hash,K key,V value) {
        // tem var
        MyNode<K,V>[] tab;
        MyNode<K,V> p;
        int len;
        int i;
        //check && find hashNode with same hash
        if((tab = table) != null && (len = tab.length) > 0 && (p = tab[i = ((len- 1) & hash)]) != null) {
            MyNode<K,V> node = null, e; K k = p.key;

            /*
            array
             */
            if(p.hash == hash && Objects.equals(k,key)) {
                node = p;
            // hash collision
            } else if((e = p.next) != null) {
                // TODO 2024/3/22 : read block tree
                do {
                    k = e.key;
                    if(e.hash == hash && Objects.equals(key,k)) {
                        node = e;
                        break;
                    }
                    p = e;
                } while((e = e.next)!= null);
            }
            if(node != null && Objects.equals(value,node.value)) {
                if(node == p) tab[i] = node.next;
                else p.next = node.next;
                --size;
                return node;
            }
        }
        return null;
    }
    private MyNode<K,V> newNode(int hash,K key,V value,MyNode<K,V> next ) {
        return new MyNode<>(hash, key, value, next);
    }

    private MyNode<K,V> getNode(K key) {
        MyNode<K,V>[] tab;
        MyNode<K,V> first,e;
        int n, hash;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & (hash = hash(key))]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                // TODO 2024/3/22 : red black tree
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
    // util

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}



