package com.pg.backend.algorithm.linkedList;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;


public class DoublyLinkedListSentinel<T> implements Iterable<T> {

    private final static String addMethodName = "add";
    private final static String removeMethodName = "remove";

    private static ReentrantLock lock = new ReentrantLock();
    static class Node<T>{
        Node prev;
        T data;
        Node next;

        public Node(Node prev, T data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    private int size = 0;



    public DoublyLinkedListSentinel() {
        head = new Node<T>(null,null,null);
        tail = new Node<T>(null,null,null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * constructor an Util
     * @param index
     * @return
     */
    private Node<T> findNode(int index){
        if(index < (size-1) / 2){
            return findNodeFrontToBack(index);
        }else{
            return findNodeBackToFrom(index);
        }
    }

    /**
     * index start from -1,find node from front to back
     * @param index
     * @return
     */
    private Node<T> findNodeFrontToBack(int index){
        int i=-1;
        for (Node<T> p =head;p != tail;p = p.next,i++){
            if(i == index){
                return p;
            }
        }
        return null;
    }

    /**
     * index start from size,find node from back to front
     * @param index
     * @return
     */
    private Node<T> findNodeBackToFrom(int index){
        int i = size;
        for (Node<T> p = tail;p != head;p = p.prev,i--){
            if(i == index){
                return p;
            }
        }
        return null;
    }

    /**
     * notification!!! this function doesnt has the ability to check the index rationality
     * if u want to call this function,please check the index`s rationality by yourself
     * @param index
     */
    public synchronized void remove(int index) throws NoSuchMethodException {
//        this way has pool efficiency
//        Node<T> prefix = findNode(index - 1);
//        Node<T> suffix = findNode(index + 1);
        Node<T> prefix = findNode(index - 1);
        Node<T> suffix = prefix.next.next;
        prefix.next = suffix;
        suffix.prev = prefix;
        Class[] cArgs = new Class[1];
        cArgs[0] = int.class;
        maintain(prefix,suffix,this.getClass().getDeclaredMethod(removeMethodName,cArgs[0]));
    }
    /**
     * 显式锁
     * @param index
     * @param object
     * @throws NoSuchMethodException
     */
//    public  void remove(int index) throws NoSuchMethodException {
//        lock.lock();  // acquire the lock
//        try {
//            // existing code...
//            Node<T> prefix = findNode(index - 1);
//            Node<T> suffix = prefix.next.next;
//            prefix.next = suffix;
//            suffix.prev = prefix;
//            Class[] cArgs = new Class[1];
//            cArgs[0] = int.class;
//            maintain(prefix,suffix,this.getClass().getDeclaredMethod(removeMethodName,cArgs[0]));
//        } finally {
//            lock.unlock();  // release the lock
//        }
//    }

    /**
     * 未优化的代码
     */
//    @Deprecated
//    public void add(int index,Node<T> tem){
//
//        Node<T> prefix = findNode(index - 1);
//        Node<T> suffix = prefix.next;
//        prefix.next = tem;
//        tem.prev = prefix;
//        suffix.prev = tem;
//        tem.next = suffix;
//    }

    /**
     * notification!!! this function doesnt has the ability to check the index rationality
     * if u want to call this function,please check the index`s rationality by yourself
     * @param index
     * @param object
     */
    private synchronized void add(int index,T object) throws NoSuchMethodException {

        Node<T> prefix = findNode(index - 1);
        Node<T> suffix = prefix.next;
        Node<T> tem = new Node<>(prefix,object, suffix);
        prefix.next = tem;
        suffix.prev = tem;

        Class[] cArgs = new Class[2];
        cArgs[0] = int.class;
        cArgs[1] = Object.class;
        maintain(prefix,suffix,this.getClass().getDeclaredMethod(addMethodName,cArgs));

    }
    /**
     * 显示锁
     */
//    private  void add(int index,T object) throws NoSuchMethodException {
//            lock.lock();
//        try {
//            Node<T> prefix = findNode(index - 1);
//            Node<T> suffix = prefix.next;
//            Node<T> tem = new Node<>(prefix,object, suffix);
//            prefix.next = tem;
//            suffix.prev = tem;
//
//            Class[] cArgs = new Class[2];
//            cArgs[0] = int.class;
//            cArgs[1] = Object.class;
//            maintain(prefix,suffix,this.getClass().getDeclaredMethod(addMethodName,cArgs));
//        } finally {
//            lock.unlock();
//        }
//    }


    public void addByIndex(int index,T object) throws NoSuchMethodException {
        isIndexIllegal(index);
        add(index,object);
    }
    public void removeByIndex(int index) throws NoSuchMethodException{
        isIndexIllegal(index);
        remove(index);
    }
    public void addFirst(T object) throws NoSuchMethodException{
        add(0,object);
    }
    public void removeFirst() throws  NoSuchMethodException{
        remove(0);
    }
    public void addLast(T object) throws NoSuchMethodException{
        add(size,object);
    }
    public void removeLast() throws NoSuchMethodException{
        remove(size - 1);
    }
    private void isIndexIllegal(int index){
        if(index < 0 && index >size){
            throw illegalIndex(index);
        }
    }
    private IllegalArgumentException illegalIndex(int index){
        return new IllegalArgumentException(String.format("index: [%d] is illegal",index));
    }

    private void quickGc(Node<T> pre,Node<T> suf){
        pre = null;
        suf = null;
    }
    private void maintain(Node<T> pre,Node<T> suf,Method method){
        quickGc(pre,suf);
        maintainSize(method);
    }

    private  void maintainSize(Method method){
        if (method.getName().equals(addMethodName)) {
            size ++;
        }
        if (method.getName().equals(removeMethodName)) {
            size --;
        }
    }

    public void eliminationLoop(){
        if (head.prev != null || tail.next != null) {
            head.prev = null;
            tail.next = null;
            return;
        }
        Node<T> bug = detectCycle();
        if (bug == null) {
            return;
        }
        Node<T> t = bug.prev;
        Node<T>[] q= getPreAndSuf(bug);
        t.next = q[1];
        q[1].prev = t;
        bug.prev = q[0];
    }
    private  Node<T> detectCycle(){
        Node<T> f = head;
        Node<T> s = head;
        while(f != null && f.next != null){
            f = f.next.next;
            s = s.next;
            if(s == f){
                s = head;
                while(true){
                    if(s == f)  return s;
                    s = s.next;
                    f = f.next;
                }
            }
        }
        return null;
    }

    private Node<T>[] getPreAndSuf(Node<T> bug){
        Node<T>[] res = new Node[2];
        Node<T> t = head,p = tail;
        while(t.next == bug && p.prev == bug){
            if(t.next != bug) t = t.next;
            if(p.prev != bug) p = p.prev;
        }
        res[0] = t;
        res[1] = p;
        return res;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> tem = head.next;
            @Override
            public boolean hasNext() {
                return tem != tail;
            }

            @Override
            public T next() {
                T oTem = tem.data;
                tem = tem.next;
                return oTem;
            }
        };
    }
}
