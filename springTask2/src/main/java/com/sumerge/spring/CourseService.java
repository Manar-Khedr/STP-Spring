package com.sumerge.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRecommender courseRecommender;

    // Constructor Injection
    @Autowired
    public CourseService(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }

    List<Course> getRecommendedCourses(){
        return courseRecommender.recommendedCourses();
    }

    // Setter Injection -> Autowired here, remove Autowired from Constructor
    // @Autowired
    public void setCourseRecommender( @Qualifier("courseRecommenderImpl1") CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }
}
