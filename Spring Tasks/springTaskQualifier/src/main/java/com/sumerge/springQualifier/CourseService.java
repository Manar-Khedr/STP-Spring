package com.sumerge.springQualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRecommender courseRecommender;

    @Autowired
    public CourseService(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }

    List<Course> getRecommendedCourses(){
        return courseRecommender.recommendedCourses();
    }

    public void setCourseRecommender( CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }
}
