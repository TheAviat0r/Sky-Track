package ru.mail.track;

/**
 * Created by Aviator on 01.10.2015.
 */
public class Human {
    private String name;
    private int    age;
//!----------------------------
    public Human (String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int    getAge() { return age; }
    public void   setName(String name) { this.name = name; }
    public void   setAge(int age) { this.age = age; }
}
