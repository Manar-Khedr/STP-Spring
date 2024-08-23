package com.sumerge.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.AuthorService;
import com.sumerge.spring.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@ContextConfiguration(classes = CourseController.class)
@ComponentScan(basePackages = "com.sumerge.spring.exception") // Adjusted the package
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseDTO courseDTO;
    private String courseName = "Test Course";
    private String courseDescription = "Test Course Description";
    private CourseController courseController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        courseDTO = new CourseDTO(courseName,courseDescription,0);
        courseController = new CourseController(courseService);
    }

    @Test
    void testAddCourse_Success() throws Exception {
        // initialize
        CourseDTO createdCourse = new CourseDTO(courseName,courseDescription,0);
        when(courseService.addCourse(any(CourseDTO.class))).thenReturn(createdCourse);

        // testing
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value(courseName));
    }

    @Test
    void testAddCourse_ValidationException() throws Exception{
        // Arrange

        doThrow(new ValidationException("Course with name Test Course already exists."))
                .when(courseService).addCourse(any(CourseDTO.class));

        // Act & Assert
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void testViewCourse_Success() throws Exception{

        when(courseService.viewCourse(courseName)).thenReturn(courseDTO);

        mockMvc.perform(get("/courses/{courseName}", courseName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value(courseName));
    }

    @Test
    void testViewAuthorByEmail_ResourceNotFoundException() throws Exception{
        // Arrange
        String courseName = "Not Found Course";

        when(courseService.viewCourse(courseName)).thenThrow(new ResourceNotFoundException("Course not found with name: " + courseName));

        // Act & Assert
        mockMvc.perform(get("/courses/{courseName}", courseName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCourse_Success() throws Exception{
        // initialize
        CourseDTO updatedCourse = new CourseDTO(courseName,courseDescription,0);

        when(courseService.updateCourse(any(CourseDTO.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/courses/{courseName}",courseName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value(courseName));
    }

    @Test
    void testUpdateCourse_ResourceNotFoundException() throws Exception{

        courseDTO.setCourseName("Not Found");

        when(courseService.updateCourse(any(CourseDTO.class))).thenThrow(new ResourceNotFoundException("Course not found with name: " + "Not Found Course"));

        mockMvc.perform(put("/courses/{courseName}", "Not Found Course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCourse_Success() throws Exception{

        doNothing().when(courseService).deleteCourse(courseName);

        // Perform the delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{courseName}", courseName))
                .andExpect(status().isOk());

        // Verify the service method was called with the correct parameter
        verify(courseService).deleteCourse(courseName);
    }

    @Test
    void testDeleteCourse_ResourceNotFoundException() throws Exception{
        // Mock the service to throw ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Course not found with name: " + "Not Found Course"))
                .when(courseService).deleteCourse("Not Found Course");

        // Perform the delete request and expect NOT_FOUND status
        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{courseName}", "Not Found Course"))
                .andExpect(status().isNotFound());

        // Verify the service method was called with the correct parameter
        verify(courseService).deleteCourse("Not Found Course");
    }

    @Test
    void testViewCourses_Success() throws Exception{
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> courseList = List.of(courseDTO);
        Page<CourseDTO> coursePage = new PageImpl<>(courseList, pageable, courseList.size());

        when(courseService.viewAllCourses(anyInt(), anyInt())).thenReturn(coursePage);

        // Act
        ResponseEntity<Page<CourseDTO>> response = courseController.viewAllCourses(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coursePage, response.getBody());
        verify(courseService, times(1)).viewAllCourses(page, size);
    }
}
