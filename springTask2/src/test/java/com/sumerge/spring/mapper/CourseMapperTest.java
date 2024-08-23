package com.sumerge.spring.mapper;
import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring3.classes.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseMapperTest {

    private final CourseMapper mapper = CourseMapper.INSTANCE;

    @Test
    public void testMapToCourseDTO() {
        // Given
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("Test Course");
        course.setCourseDescription("Test Course Description");
        course.setCourseCredit(5);

        // When
        CourseDTO courseDTO = mapper.mapToCourseDTO(course);

        // Then
        assertEquals(course.getCourseId(), courseDTO.getCourseId());
        assertEquals(course.getCourseName(), courseDTO.getCourseName());
        assertEquals(course.getCourseDescription(), courseDTO.getCourseDescription());
        assertEquals(course.getCourseCredit(), courseDTO.getCourseCredit());
    }

    @Test
    public void testMapToCourse() {
        // Given
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(1);
        courseDTO.setCourseName("Test Course");
        courseDTO.setCourseDescription("Test Course Description");
        courseDTO.setCourseCredit(5);

        // When
        Course course = mapper.mapToCourse(courseDTO);

        // Then
        assertEquals(courseDTO.getCourseId(), course.getCourseId());
        assertEquals(courseDTO.getCourseName(), course.getCourseName());
        assertEquals(courseDTO.getCourseDescription(), course.getCourseDescription());
        assertEquals(courseDTO.getCourseCredit(), course.getCourseCredit());
    }
}
