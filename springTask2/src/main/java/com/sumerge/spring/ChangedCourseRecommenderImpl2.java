package com.sumerge.spring;
import com.sumerge.spring3.Course;
import com.sumerge.spring3.CourseRecommenderImpl2;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

@Qualifier("courseRecommenderImpl2")
public class ChangedCourseRecommenderImpl2 extends CourseRecommenderImpl2 {

    @Override
    public List<Course> recommendedCourses() {
        // Custom behavior
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CourseRecommenderImpl2 Overridden to Course 5" , 5, "Computer Networks 1", 3));
        courseList.add(new Course("CourseRecommenderImpl2 Overridden to Course 6", 6, "Advanced Networks", 3));
        return courseList;
    }
}
