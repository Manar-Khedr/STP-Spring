package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring3.classes.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    Course mapToCourse(CourseDTO courseDTO);
    CourseDTO mapToCourseDTO(Course course);
}
