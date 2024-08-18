package com.sumerge.spring.config;

import com.sumerge.spring.CourseRecommenderImpl1;
import com.sumerge.spring.mapper.AuthorMapper;
import com.sumerge.spring.mapper.CourseMapper;
import com.sumerge.spring.repository.AuthorRepository;
import com.sumerge.spring.service.AuthorService;
import com.sumerge.spring.service.CourseService;
import com.sumerge.spring.repository.CourseRepository;
import com.sumerge.spring3.CourseRecommender;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;
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

//    @Bean
//    public CourseService courseService(CourseRepository courseRepository, CourseMapper courseMapper) {
//        return new CourseService(courseRepository, courseMapper);
//    }
//
//    @Bean
//    public AuthorService authorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
//        return new AuthorService(authorRepository, authorMapper);
//    }

    @Bean
    public CourseMapper courseMapper() {
        return Mappers.getMapper(CourseMapper.class);
    }

    @Bean
    public AuthorMapper authorMapper() {
        return Mappers.getMapper(AuthorMapper.class);
    }

}
