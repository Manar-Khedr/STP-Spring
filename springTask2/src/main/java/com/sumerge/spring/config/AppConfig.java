package com.sumerge.spring.config;

import com.sumerge.spring.ChangedCourseRecommenderImpl2;
import com.sumerge.spring.CourseRecommenderImpl1;
import com.sumerge.spring.CourseService;
import com.sumerge.spring.CourseRepository;
import com.sumerge.spring.CourseRepositoryImpl;
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
            CourseRepository courseRepository) {
        return new CourseService(courseRecommender, courseRepository);
    }

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

    @Bean
    public CourseRepository courseRepository(JdbcTemplate jdbcTemplate) {
        return new CourseRepositoryImpl(jdbcTemplate);
    }
}
