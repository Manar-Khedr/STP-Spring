package com.sumerge.spring.dto;

import java.util.*;

public class AuthorDTO {

    private int authorId;
    private String authorName;
    private String authorEmail;
    private Date authorBirthDate;

    //Default constructor
    public AuthorDTO(){
    }

    public AuthorDTO(String authorEmail, String authorName){
        this.authorEmail = authorEmail;
        this.authorName = authorName;
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
