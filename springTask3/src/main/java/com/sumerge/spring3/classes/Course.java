package com.sumerge.spring3.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    private String courseName;
    private String courseDescription;
    private int courseCredit;
    private int courseDuration;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private Assessment assessment;

    @ManyToMany(mappedBy = "courses")
    private Set<Author> authors;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Rating> ratings;

    // Default constructor
    public Course() {}

    public Course(String courseName, String courseDescription, int courseCredit, int duration){
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseCredit = courseCredit;
        this.courseDuration = duration;
    }

    // Getters and setters
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }
}
