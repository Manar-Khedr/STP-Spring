package com.sumerge.spring.config;

import com.sumerge.spring.ChangedCourseRecommenderImpl2;
import com.sumerge.spring.CourseRecommenderImpl1;
import com.sumerge.spring.CourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.sumerge.spring3.CourseRecommender;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = "com.sumerge.spring")
@Import(DataSourceConfig.class)
public class AppConfig {


    @Bean
    public CourseService courseService(
            @Qualifier("courseRecommenderImpl2") CourseRecommender courseRecommender,
            JdbcTemplate jdbcTemplate) {
        return new CourseService(jdbcTemplate,courseRecommender);
    }

    /////////////////

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

}