package com.sumerge.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.AuthorService;
import com.sumerge.spring.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ValidationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RatingController.class)
@ContextConfiguration(classes = RatingController.class)
@ComponentScan(basePackages = "exception")
@ExtendWith(SpringExtension.class)
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

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
        // initialize
        RatingDTO createdRating = new RatingDTO(ratingId,ratingNumber);
        when(ratingService.addRating(any(RatingDTO.class))).thenReturn(createdRating);

        // testing
        mockMvc.perform(post("/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId").value(ratingId));
    }

    @Test
    void testAddRating_ValidationException() throws Exception{
        // Arrange

        doThrow(new ValidationException("Rating with ID 1 already exists."))
                .when(ratingService).addRating(any(RatingDTO.class));

        // Act & Assert
        mockMvc.perform(post("/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isConflict());
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
    void testViewRating_ResourceNotFoundException() throws Exception{
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
        RatingDTO updatedRating = new RatingDTO(ratingId,ratingNumber);

        when(ratingService.updateRating(any(RatingDTO.class))).thenReturn(updatedRating);

        mockMvc.perform(put("/ratings/{ratingId}",ratingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId").value(ratingId));
    }

    @Test
    void testUpdateRating_ResourceNotFoundException() throws Exception{

        ratingDTO.setRatingId(2);

        when(ratingService.updateRating(any(RatingDTO.class))).thenThrow(new ResourceNotFoundException("Rating not found with ID: " + "2"));

        mockMvc.perform(put("/ratings/{rating}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRating_Success() throws Exception{

        doNothing().when(ratingService).deleteRating(ratingId);

        // Perform the delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/ratings/{ratingId}", ratingId))
                .andExpect(status().isNoContent());

        // Verify the service method was called with the correct parameter
        verify(ratingService).deleteRating(ratingId);
    }

    @Test
    void testDeleteRating_ResourceNotFoundException() throws Exception{
        // Mock the service to throw ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Rating not found with ID: " + 2))
                .when(ratingService).deleteRating(2);

        // Perform the delete request and expect NOT_FOUND status
        mockMvc.perform(MockMvcRequestBuilders.delete("/ratings/{ratingId}", 2))
                .andExpect(status().isNotFound());

        // Verify the service method was called with the correct parameter
        verify(ratingService).deleteRating(2);
    }

}
