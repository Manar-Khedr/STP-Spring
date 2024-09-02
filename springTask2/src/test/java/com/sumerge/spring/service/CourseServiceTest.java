package com.sumerge.spring.service;

import com.sumerge.spring.controller.CourseController;
import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.CourseMapper;
import com.sumerge.spring.repository.CourseRepository;
import com.sumerge.spring3.classes.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private CourseDTO courseDTO;
    private Course savedCourse;
    private String courseName = "Test Course";
    private String courseDescription = "Test Course Description";
    private CourseController courseController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        courseDTO = new CourseDTO("Test Course","Test Course Description", 0,1);
        course = new Course();
        savedCourse = new Course();
        // assign id
        savedCourse.setCourseId(1);
        courseController = new CourseController(courseService);
    }

    @Test
    void testAddCourse_Success() throws ValidationException {
        // testing
        when(courseRepository.findByCourseName(courseDTO.getCourseName())).thenReturn(Optional.empty());
        when(courseMapper.mapToCourse(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(savedCourse);
        when(courseMapper.mapToCourseDTO(savedCourse)).thenReturn(courseDTO);

        // check results
        CourseDTO result = courseService.addCourse(courseDTO);

        assertNotNull(result,"The result should not be null");
        assertEquals(courseDTO.getCourseName(),result.getCourseName());
        assertEquals(courseDTO.getCourseDescription(),result.getCourseDescription());
    }

    @Test
    void testAddCourse_ValidationException(){

        when(courseRepository.findByCourseName(courseDTO.getCourseName())).thenReturn(Optional.of(new Course()));

        // exception
        ValidationException exception = assertThrows(ValidationException.class, () -> courseService.addCourse(courseDTO));

        // check results
        assertEquals("Course with name Test Course already exists.",exception.getMessage());
    }

    @Test
    void testViewCourse_Success() throws ResourceNotFoundException{
        //initialize
        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);

        // arrange
        when(courseRepository.findByCourseName(courseName)).thenReturn(Optional.of(course));
        // mapping
        when(courseMapper.mapToCourseDTO(course)).thenReturn(courseDTO);

        // check results
        CourseDTO result = courseService.viewCourse(courseName);

        assertNotNull(result,"The result should not be null");
        assertEquals(courseName,result.getCourseName());
        assertEquals(courseDescription, result.getCourseDescription());
    }

    @Test
    void testViewCourse_ResourseNotFoundException(){

        when(courseRepository.findByCourseName(courseName)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> courseService.viewCourse("Not Found Course"));

        assertEquals("Course not found with name: Not Found Course",exception.getMessage());
    }

    @Test
    void testUpdateCourse_Success() throws ResourceNotFoundException{
        // initiliaze
        course.setCourseName(courseName);
        course.setCourseDescription("Updated Course Description");
        courseDTO.setCourseDescription("Updated Course Description");

        when(courseRepository.findByCourseName(courseName)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.mapToCourseDTO(course)).thenReturn(courseDTO);

        // check results
        CourseDTO result = courseService.updateCourse(courseDTO);

        assertNotNull(result,"The result should not be null");
        assertEquals("Updated Course Description", result.getCourseDescription());
    }

    @Test
    void testUpdateCourse_ResourceNotFoundException(){
        when(courseRepository.findByCourseName("Not Found Course")).thenReturn(Optional.empty());

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseName("Not Found Course");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(courseDTO));

        assertEquals("Course not found with name: Not Found Course", exception.getMessage());
    }

    @Test
    void testDeleteCourse_Success() throws ResourceNotFoundException{
        // initailize
        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);

        when(courseRepository.findByCourseName(courseName)).thenReturn(Optional.of(course));

        // checl results
        courseService.deleteCourse(courseName);
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void testDeleteCourse_ResourceNotFoundException(){
        when(courseRepository.findByCourseName(courseName)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse("Not Found Course"));

        assertEquals("Course not found with name: Not Found Course",exception.getMessage());
    }

    @Test
    void testViewAllAuthors_Success(){
        // Arrange
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        List<Course> courses = Arrays.asList(course);
        Page<Course> coursePage = new PageImpl<>(courses, pageable, courses.size());

        when(courseRepository.findAll(pageable)).thenReturn(coursePage);
        when(courseMapper.mapToCourseDTO(course)).thenReturn(courseDTO);

        // Act
        Page<CourseDTO> result = courseService.viewAllCourses(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(courseDTO, result.getContent().get(0));
    }
}
