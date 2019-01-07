package com.wumin.boot152.test;

public class Teacher {
    Student student;

    public Teacher(Student student){
        System.out.println("teacher 无参构造");
        System.out.println(student);
        this.student=student;
    }
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
