package com.sumerge.spring.dto;

public class RatingDTO {

    private int ratingId;
    private int number;

    public RatingDTO(int ratingId, int number){
        this.ratingId = ratingId;
        this.number = number;
    }

    public RatingDTO(){}

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
