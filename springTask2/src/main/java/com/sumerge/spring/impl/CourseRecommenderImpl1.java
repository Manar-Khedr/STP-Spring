package com.sumerge.spring.impl;

import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.classes.Course;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

public class CourseRecommenderImpl1 implements CourseRecommender {
    @Override
    public List<Course> recommendedCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Course 3","Description 3",1,1));
        courseList.add(new Course("Course 4","Description 4",1,1));
        return courseList;
    }
}