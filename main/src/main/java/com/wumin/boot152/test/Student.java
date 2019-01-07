package com.wumin.boot152.test;

public class Student {
    private String name;
    private Integer age;
    public Student(){
        System.out.println("Student 无参构造");
        this.name="wumin";
        this.age=22;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
