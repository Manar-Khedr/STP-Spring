package com.sumerge.spring3;

import com.sumerge.spring3.classes.Course;

import java.util.List;

public interface CourseRecommender {

    List<Course> recommendedCourses();
}
