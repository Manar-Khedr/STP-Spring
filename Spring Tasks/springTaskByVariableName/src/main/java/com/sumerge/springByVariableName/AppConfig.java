package com.sumerge.springByVariableName;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CourseRecommender courseRecommenderImpl1() {
        return new CourseRecommenderImpl1();
    }

    @Bean
    public CourseRecommender courseRecommenderImpl2() {
        return new CourseRecommenderImpl2();
    }

    @Bean
    public CourseService courseService( CourseRecommender courseRecommenderImpl2) {
        return new CourseService(courseRecommenderImpl2);
    }
}
