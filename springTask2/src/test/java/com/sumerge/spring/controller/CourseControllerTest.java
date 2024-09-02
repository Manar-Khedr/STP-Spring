package com.sumerge.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.spring.config.SecurityConfig;
import com.sumerge.spring.config.TestSecurityConfig;
import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.CourseService;
import com.sumerge.spring.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
@Import({SecurityConfig.class, TestSecurityConfig.class})
@ContextConfiguration(classes = CourseController.class)
@ComponentScan(basePackages = "exception")
@ExtendWith(SpringExtension.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseDTO courseDTO;
    private String courseName = "Test Course";
    private String courseDescription = "Test Course Description";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseDTO = new CourseDTO(courseName, courseDescription, 0,1);
    }

    // Add Course Tests
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testAddCourse_Authorized() throws Exception {
        when(securityService.isAdmin()).thenReturn(true);
        when(courseService.addCourse(any(CourseDTO.class))).thenReturn(courseDTO);

        mockMvc.perform(post("/courses/add")
                        .header("Authorization", "Admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value(courseName));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testAddCourse_Unauthorized() throws Exception {
        when(securityService.isAdmin()).thenReturn(false);

        mockMvc.perform(post("/courses/add")
                        .header("Authorization", "InvalidToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isForbidden());
    }

    // Update Course Tests
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testUpdateCourse_Authorized() throws Exception {
        when(securityService.isAdmin()).thenReturn(true);
        when(courseService.updateCourse(any(CourseDTO.class))).thenReturn(courseDTO);

        mockMvc.perform(put("/courses/update/{courseName}", courseName)
                        .header("Authorization", "Admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value(courseName));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testUpdateCourse_Unauthorized() throws Exception {
        when(securityService.isAdmin()).thenReturn(false);

        mockMvc.perform(put("/courses/update/{courseName}", courseName)
                        .header("Authorization", "InvalidToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isForbidden());
    }

    // Delete Course Tests
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testDeleteCourse_Authorized() throws Exception {
        when(securityService.isAdmin()).thenReturn(true);
        doNothing().when(courseService).deleteCourse(courseName);

        mockMvc.perform(delete("/courses/delete/{courseName}", courseName)
                        .header("Authorization", "Admin"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testDeleteCourse_Unauthorized() throws Exception {
        when(securityService.isAdmin()).thenReturn(false);

        mockMvc.perform(delete("/courses/delete/{courseName}", courseName)
                        .header("Authorization", "InvalidToken"))
                .andExpect(status().isForbidden());
    }

    // View All Courses Tests
    @Test
    @WithMockUser(roles = "ADMIN")
    void testViewAllCourses_Authorized() throws Exception {
        Page<CourseDTO> coursePage = new PageImpl<>(List.of(courseDTO), PageRequest.of(0, 10), 1);
        when(courseService.viewAllCourses(anyInt(), anyInt())).thenReturn(coursePage);

        mockMvc.perform(get("/courses/discover")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].courseName").value(courseName));
    }

    @Test
    void testViewAllCourses_Unauthorized() throws Exception {
        mockMvc.perform(get("/courses/discover")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "InvalidToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
