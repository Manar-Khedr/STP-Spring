package com.sumerge.spring3;

public class Course {

    private String courseName;
    private int courseId;
    private String courseDescription;
    private int courseCredit;

    public Course(String courseName, int courseId, String courseDescription, int courseCredit) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseDescription = courseDescription;
        this.courseCredit = courseCredit;
    }

    // Getters
    public String getName() {
        return courseName;
    }

    public int getId() {
        return courseId;
    }

    public String getDescription() {
        return courseDescription;
    }

    public int getCredit() {
        return courseCredit;
    }

    // Setters
    public void setName(String courseName){
        this.courseName = courseName;
    }

    public void setId(int courseId){
        this.courseId = courseId;
    }

    public void setDescription(String courseDescription){
        this.courseDescription = courseDescription;
    }

    public void setCredit(int courseCredit){
        this.courseCredit = courseCredit;
    }
}
