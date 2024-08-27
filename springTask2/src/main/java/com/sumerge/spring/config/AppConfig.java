package com.sumerge.spring.config;

import com.sumerge.spring.mapper.AssessmentMapper;
import com.sumerge.spring.mapper.AuthorMapper;
import com.sumerge.spring.mapper.CourseMapper;
import com.sumerge.spring.mapper.RatingMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

}
