package com.sumerge.spring.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Author")
public class Author {

    // variables
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int authorId;

    @JsonProperty("name")
    @Column(name = "name")
    private String authorName;

    @JsonProperty("email")
    @Column(name = "email")
    private String authorEmail;

    @JsonProperty("birthdate")
    @Column(name = "birthdate")
    private Date authorBirthDate;

    // default constructor
    public Author(){

    }

    public Author(int authorId, String authorName, String authorEmail, Date authorBirthDate){
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorBirthDate = authorBirthDate;
    }


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
}
