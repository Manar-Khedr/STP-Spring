package com.sumerge.spring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.Course;
import com.sumerge.spring3.CourseRecommenderImpl2;

@Configuration
@ComponentScan(basePackages = "com.sumerge.spring")
public class AppConfig {

    @Bean
    @Qualifier("courseRecommenderImpl1")
    public CourseRecommender courseRecommenderImpl1() {
        return new CourseRecommenderImpl1();
    }

    @Bean
    @Qualifier("courseRecommenderImpl2")
    public CourseRecommender courseRecommenderImpl2() {
        return new ChangedCourseRecommenderImpl2();
    }

    // Uncomment only when needed to see the OLD CourseRecommenderImpl2 before overriding
    // BUT comment the code above
//    @Bean
//    @Qualifier("courseRecommenderImpl2")
//    public CourseRecommender courseRecommenderImpl2() {
//        return new CourseRecommenderImpl2();
//    }

    @Bean
    public CourseService courseService(@Qualifier("courseRecommenderImpl2") CourseRecommender courseRecommender) {
        return new CourseService(courseRecommender);
    }

}