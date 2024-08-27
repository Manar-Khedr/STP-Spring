package com.sumerge.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.spring.config.SecurityConfig;
import com.sumerge.spring.config.TestSecurityConfig;
import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.RatingService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ValidationException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({SecurityConfig.class, TestSecurityConfig.class})
@WebMvcTest(controllers = RatingController.class)
@ContextConfiguration(classes = RatingController.class)
@ComponentScan(basePackages = "exception")
@ExtendWith(SpringExtension.class)
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private ObjectMapper objectMapper;

    private RatingController ratingController;



    private RatingDTO ratingDTO;
    private int ratingId = 1;
    private int ratingNumber = 1;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ratingDTO = new RatingDTO(ratingId,ratingNumber);
        ratingController = new RatingController(ratingService);
    }

    @Test
    void testAddRating_Success() throws Exception {

        when(securityService.isAdmin()).thenReturn(true);
        when(ratingService.addRating(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(post("/ratings/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId").value(ratingId));
    }

    @Test
    void testAddRating_Authorized_AlreadyExists() throws Exception {
        // Arrange for ValidationException
        doThrow(new ValidationException("Rating with ID 1 already exists."))
                .when(ratingService).addRating(any(RatingDTO.class));
        // Mock security service to allow admin access for this test
        when(securityService.isAdmin()).thenReturn(true);

        // Act & Assert for ValidationException
        mockMvc.perform(post("/ratings/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isConflict());

        System.out.println("ValidationException test passed.");
    }

    @Test
    void testAddRating_Unauthorized() throws Exception {
        // Arrange for Unauthorized Access
        when(securityService.isAdmin()).thenReturn(false);

        // Act & Assert for Unauthorized Access
        mockMvc.perform(post("/ratings/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isForbidden());

        System.out.println("Unauthorized Access test passed.");
    }

    @Test
    void testViewRating_Success() throws Exception{

        when(ratingService.viewRating(ratingId)).thenReturn(ratingDTO);

        mockMvc.perform(get("/ratings/{ratingId}", ratingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId").value(ratingId));
    }

    @Test
    void testViewRating_NotFound() throws Exception{
        // Arrange
        int ratingId = 2;

        when(ratingService.viewRating(ratingId)).thenThrow(new ResourceNotFoundException("Rating not found with ID: " + ratingId));

        // Act & Assert
        mockMvc.perform(get("/ratings/{ratingId}", ratingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateRating_Success() throws Exception{
        // initialize
        when(securityService.isAdmin()).thenReturn(true);
        when(ratingService.updateRating(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(put("/ratings/update/{ratingId}",ratingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId").value(ratingId));
    }

    @Test
    void testUpdateRating_Authorized_NotFound() throws Exception{

        ratingDTO.setRatingId(2);
        when(securityService.isAdmin()).thenReturn(true);
        when(ratingService.updateRating(any(RatingDTO.class))).thenThrow(new ResourceNotFoundException("Rating not found with ID: " + "2"));

        mockMvc.perform(put("/ratings/update/{rating}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateRating_Unauthorized() throws Exception{
        // Arrange for Unauthorized Access
        when(securityService.isAdmin()).thenReturn(false);

        mockMvc.perform(put("/ratings/update/{ratingId}", ratingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteRating_Success() throws Exception{
        when(securityService.isAdmin()).thenReturn(true);
        doNothing().when(ratingService).deleteRating(ratingId);

        // Perform the delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/ratings/delete/{ratingId}", ratingId))
                .andExpect(status().isNoContent());

        // Verify the service method was called with the correct parameter
        verify(ratingService).deleteRating(ratingId);
    }

    @Test
    void testDeleteRating_Authroized_NotFound() throws Exception{
        // Mock the service to throw ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Rating not found with ID: " + 2))
                .when(ratingService).deleteRating(2);
        when(securityService.isAdmin()).thenReturn(true);
        // Perform the delete request and expect NOT_FOUND status
        mockMvc.perform(MockMvcRequestBuilders.delete("/ratings/delete/{ratingId}", 2))
                .andExpect(status().isNotFound());

        // Verify the service method was called with the correct parameter
        verify(ratingService).deleteRating(2);
    }

    @Test
    void testDeleteRating_Unauthorized() throws Exception{
        when(securityService.isAdmin()).thenReturn(false);

        mockMvc.perform(delete("/ratings/delete/{ratingId}", ratingId))
                .andExpect(status().isForbidden());
    }

}
