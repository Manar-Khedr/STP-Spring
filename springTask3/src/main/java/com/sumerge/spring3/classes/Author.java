package com.sumerge.spring3.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;

    private String authorName;
    private String authorEmail;
    private Date authorBirthDate;

    @ManyToMany
    @JoinTable(
            name = "course_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    // Default constructor
    public Author() {}

    public Author(String authorName, String authorEmail){
        this.authorEmail = authorEmail;
        this.authorName = authorName;
    }

    // Getters and setters
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Date getAuthorBirthDate() {
        return authorBirthDate;
    }

    public void setAuthorBirthDate(Date authorBirthDate) {
        this.authorBirthDate = authorBirthDate;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
