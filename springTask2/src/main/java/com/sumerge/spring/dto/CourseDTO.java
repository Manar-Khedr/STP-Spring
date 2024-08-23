package com.sumerge.spring.dto;



public class CourseDTO {

    private int courseId;
    private String courseName;
    private String courseDescription;
    private int courseCredit;

    // default constructor
    public CourseDTO(){}

    public CourseDTO(String courseName, String courseDescription, int courseCredit){
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseCredit = courseCredit;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }
}
