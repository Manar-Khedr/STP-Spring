package com.sumerge.spring;

import com.sumerge.spring3.Course;
import java.util.*;

public interface CourseRepository {

    void addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(Course course);
    Course viewCourse(int courseId);
    List<Course> viewAllCourses();
}
