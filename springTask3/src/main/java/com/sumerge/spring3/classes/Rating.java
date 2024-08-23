package com.sumerge.spring3.classes;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingId;

    private int number;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Rating(int ratingId, int number){
        this.ratingId = ratingId;
        this.number = number;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
