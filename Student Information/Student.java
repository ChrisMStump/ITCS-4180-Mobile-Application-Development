package com.example.chris.inclass03;

import java.io.Serializable;

/**
 * Created by Chris on 5/30/2017.
 */

public class Student implements Serializable{
    private String name, email, department;
    private int mood;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public int getMood() {
        return mood;
    }

    public Student(String name, String email, String department, int mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }


}
