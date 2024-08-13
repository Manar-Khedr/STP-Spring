package com.sumerge.springByVariableName;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseRecommenderImpl1 implements CourseRecommender{

    @Override
    public List<Course> recommendedCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Course 1"));
        courseList.add(new Course("Course 2"));

        return courseList;
    }
}
