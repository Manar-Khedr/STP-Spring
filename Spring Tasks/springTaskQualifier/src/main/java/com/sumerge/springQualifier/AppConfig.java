package com.sumerge.springQualifier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    public CourseService courseService(@Qualifier("courseRecommenderImpl1") CourseRecommender courseRecommender) {
        return new CourseService(courseRecommender);
    }
}

