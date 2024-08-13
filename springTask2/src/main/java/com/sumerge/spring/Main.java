package com.sumerge.spring;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.Course;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import com.sumerge.spring3.Course;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1
        explicitDeclare();
        // 2/3
        qualifierDifferentiate();
        // 4
        changedQualifier();

    }

    public static void explicitDeclare(){

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseRecommender courseRecommenderImpl1 = context.getBean("courseRecommenderImpl1" ,CourseRecommender.class);
        List<Course> recommendedCourses = courseRecommenderImpl1.recommendedCourses();
        for (Course course : recommendedCourses) {
            System.out.println("Recommended course: " + course.getName());
        }
        context.close();
    }

    public static void qualifierDifferentiate(){

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseService courseService = context.getBean(CourseService.class);
        List<Course> courses = courseService.getRecommendedCourses();
        courses.forEach(course -> System.out.println("Recommended Course: "+course.getName()));
    }

    public static void changedQualifier(){

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseService courseService = context.getBean(CourseService.class);
        List<Course> courses = courseService.getRecommendedCourses();
        courses.forEach(course -> System.out.println("Recommended Course: "+course.getName()));

    }
}
