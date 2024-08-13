package com.sumerge.springConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public CourseRecommender courseRecommenderImpl1() {
        return new CourseRecommenderImpl1();
    }

    @Bean
    public CourseRecommender courseRecommenderImpl2() {
        return new CourseRecommenderImpl2();
    }

    @Bean
    public CourseService courseService( CourseRecommender courseRecommender) {
        return new CourseService(courseRecommender);
    }
}

