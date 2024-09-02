package com.sumerge.spring.exception;

public class CourseRecommendationException extends RuntimeException {
    public CourseRecommendationException(String message) {
        super(message);
    }

    public CourseRecommendationException(String message, Throwable cause) {
        super(message, cause);
    }
}