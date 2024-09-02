package com.sumerge.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Course data transfer object")
public class CourseDTO {

    @Schema(description = "Unique identifier for the course", example = "1")
    private int courseId;

    @Schema(description = "Name of the course", example = "Introduction to Programming")
    private String courseName;

    @Schema(description = "Description of the course", example = "A beginner's course in programming.")
    private String courseDescription;

    @Schema(description = "Number of credits for the course", example = "3")
    private int courseCredit;

    @Schema(description = "Number of duration for the course", example = "3")
    private int courseDuration;




    // Default constructor
    public CourseDTO() {}

    // Parameterized constructor
    public CourseDTO(String courseName, String courseDescription, int courseCredit, int courseDuration) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseCredit = courseCredit;
        this.courseDuration = courseDuration;
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

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }
}
