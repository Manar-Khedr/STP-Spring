package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring3.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "courseId", target = "id")
    @Mapping(source = "courseName", target = "name")
    @Mapping(source = "courseDescription", target = "description")
    @Mapping(source = "courseCredit", target = "credit")
    Course mapToCourse(CourseDTO courseDTO);

    @Mapping(source = "id", target = "courseId")
    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "description", target = "courseDescription")
    @Mapping(source = "credit", target = "courseCredit")
    CourseDTO mapToCourseDTO(Course course);
}
