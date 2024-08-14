package com.sumerge.spring;
import com.sumerge.spring.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.Course;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // JDBC Test
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseService courseService = context.getBean(CourseService.class);
        CourseRecommender courseRecommender = context.getBean("courseRecommenderImpl2",CourseRecommender.class);
        JdbcRunner(courseService, courseRecommender);

    }

    // Methods

    public static void explicitDeclare(){

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseRecommender courseRecommenderImpl1 = context.getBean("courseRecommenderImpl2" ,CourseRecommender.class);
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

    public static void JdbcRunner(CourseService courseService, CourseRecommender courseRecommender) {
        try {

            List<Course> recommendedCourses = courseRecommender.recommendedCourses();


            System.out.println("Add Courses to Database");
            for (Course course : recommendedCourses) {
                courseService.addCourse(course);
                System.out.println("Added course: " + course.getName());
            }
            System.out.println("--------------------------");


            System.out.println("View Courses from Database");
            if (!recommendedCourses.isEmpty()) {
                Course firstCourse = recommendedCourses.get(0);
                Course currentCourse = courseService.viewCourse(firstCourse.getId());
                if (currentCourse != null) {
                    System.out.println("Viewed course: " + currentCourse.getName());
                } else {
                    System.out.println("Course not found.");
                }

                System.out.println("--------------------------");

                // Update the course description
                System.out.println("Update Course");
                currentCourse.setDescription("Updated description for " + currentCourse.getName());
                courseService.updateCourse(currentCourse);
                System.out.println("Updated course description: " + courseService.viewCourse(firstCourse.getId()).getDescription());
                System.out.println("--------------------------");


                System.out.println("Delete Course from Database");
                courseService.deleteCourse(currentCourse);
                System.out.println("Deleted course, attempting to fetch again.");
                Course deletedCourse = courseService.viewCourse(firstCourse.getId());
                if (deletedCourse == null) {
                    System.out.println("Course successfully deleted.");
                } else {
                    System.out.println("Failed to delete course.");
                }
            } else {
                System.out.println("No recommended courses to process.");
            }
            System.out.println("--------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
