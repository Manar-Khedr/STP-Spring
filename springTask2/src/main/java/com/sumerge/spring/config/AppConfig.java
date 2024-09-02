package com.sumerge.spring.config;


import com.sumerge.spring.impl.CourseRecommenderImpl1;
import com.sumerge.spring.impl.SoapCourseRecommender;
import com.sumerge.spring.mapper.AssessmentMapper;
import com.sumerge.spring.mapper.AuthorMapper;
import com.sumerge.spring.mapper.CourseMapper;
import com.sumerge.spring.mapper.RatingMapper;
import com.sumerge.spring3.CourseRecommender;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.sumerge.spring")
@EnableJpaRepositories(basePackages = "com.sumerge.spring.repository")
@EntityScan(basePackages = {"com.sumerge.spring3", "com.sumerge.spring.classes"})
public class AppConfig {

    @Bean
    public CourseMapper courseMapper() {
        return Mappers.getMapper(CourseMapper.class);
    }

    @Bean
    public AuthorMapper authorMapper() {
        return Mappers.getMapper(AuthorMapper.class);
    }

    @Bean
    public AssessmentMapper assessmentMapper() {
        return Mappers.getMapper(AssessmentMapper.class);
    }

    @Bean
    public RatingMapper ratingMapper() {
        return Mappers.getMapper(RatingMapper.class);
    }

//    @Bean
//    @Qualifier("mockCourseRecommenderImpl1")
//    public CourseRecommender courseRecommender(RestTemplate restTemplate) {
//        return new CourseRecommenderImpl1(restTemplate);
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public CourseRecommender soapCourseRecommender(RestTemplate restTemplate) {
        return new SoapCourseRecommender(restTemplate);
    }

    @Bean
    @Qualifier("mockCourseRecommenderImpl1")
    public CourseRecommender courseRecommenderImpl1() {
        return new CourseRecommenderImpl1();
    }

}
