package com.sumerge.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.Course;

import java.util.List;

public class CourseService {

    private CourseRecommender courseRecommender;

    // Constructor Injection
    @Autowired
    public CourseService( CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }

}
