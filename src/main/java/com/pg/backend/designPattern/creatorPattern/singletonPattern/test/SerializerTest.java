package com.pg.backend.designPattern.creatorPattern.singletonPattern.test;

import com.pg.backend.designPattern.creatorPattern.singletonPattern.Singleton1;

import java.io.*;

/*
序列化造成单例失效
 * @author paul 2024/2/29
 */

public class SerializerTest {
    private static final String  filePath = "F:\\test.txt";
//    public static void main(String[] args)throws Exception {
//        writeObjectFormFile();
//        readObjectFormFile();
//        readObjectFormFile();
//        readObjectFormFile();
//        readObjectFormFile();
//    }
    public static void readObjectFormFile ()throws Exception{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
        /* 注意readObject的实现 */
        Singleton1 o = (Singleton1) objectInputStream.readObject();
        System.out.println(o);
        objectInputStream.close();
    }
    public static void writeObjectFormFile() throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        objectOutputStream.writeObject(Singleton1.getSingleton1());
        objectOutputStream.close();
    }

}
