package com.sumerge.springSetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRecommender courseRecommender;

    public CourseService(CourseRecommender courseRecommender){
        this.courseRecommender = courseRecommender;
    }

    List<Course> getRecommendedCourses(){
        return courseRecommender.recommendedCourses();
    }

    @Autowired
    public void setCourseRecommender( CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }


}
