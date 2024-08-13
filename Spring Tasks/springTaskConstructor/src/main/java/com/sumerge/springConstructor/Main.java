package com.sumerge.springConstructor;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CourseService courseService = context.getBean(CourseService.class);
        List<Course> recommendedCourses = courseService.getRecommendedCourses();

        recommendedCourses.forEach(course -> System.out.println(course.getName()));

        context.close();
    }
}

