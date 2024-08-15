package com.sumerge.spring3;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {

    @JsonProperty("name")
    private String courseName;
    @JsonProperty("id")
    private int courseId;
    @JsonProperty("description")
    private String courseDescription;
    @JsonProperty("credit")
    private int courseCredit;

   public Course(){

   }

    public Course(String courseName, int courseId, String courseDescription, int courseCredit) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseDescription = courseDescription;
        this.courseCredit = courseCredit;
    }

    // Getters
    @JsonProperty("name")
    public String getName() {
        return courseName;
    }
    @JsonProperty("id")
    public int getId() {
        return courseId;
    }
    @JsonProperty("description")
    public String getDescription() {
        return courseDescription;
    }
    @JsonProperty("credit")
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
