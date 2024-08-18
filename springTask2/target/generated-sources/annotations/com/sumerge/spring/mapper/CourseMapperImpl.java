package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring3.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T18:26:52+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.23 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public Course mapToCourse(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( courseDTO.getCourseId() );
        course.setName( courseDTO.getCourseName() );
        course.setDescription( courseDTO.getCourseDescription() );
        course.setCredit( courseDTO.getCourseCredit() );

        return course;
    }

    @Override
    public CourseDTO mapToCourseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setCourseId( course.getId() );
        courseDTO.setCourseName( course.getName() );
        courseDTO.setCourseDescription( course.getDescription() );
        courseDTO.setCourseCredit( course.getCredit() );

        return courseDTO;
    }
}
