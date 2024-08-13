package com.sumerge.springQualifier;

public class Course {

    private String courseName;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getName() {
        return courseName;
    }

    public void setName(String courseName) {
        this.courseName = courseName;
    }
}