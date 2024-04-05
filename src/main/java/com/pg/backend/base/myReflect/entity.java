package com.pg.backend.base.myReflect;

/**
 * @author paul 2024/4/3
 */

public class entity {
    String name;

    int age;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        System.out.println("reflectTest");
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
