package com.yztc.com.yztc.bean;

/**
 * Created by sqq on 16/5/30.
 */
public class Person {
    private int _id;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int age;

    public Person(int age, int _id, String name) {
        this.age = age;
        this._id = _id;
        this.name = name;
    }
}
