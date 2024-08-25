package com.sumerge.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AssessmentController.class)
@ContextConfiguration(classes = AssessmentController.class)
@ComponentScan(basePackages = "exception")
@ExtendWith(SpringExtension.class)
public class AssessmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssessmentService assessmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private AssessmentController assessmentController;

    private AssessmentDTO assessmentDTO;
    private int assessmentId = 1;
    private String assessmentContent = "Test Assessment Content";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assessmentDTO = new AssessmentDTO(assessmentId, assessmentContent);
        assessmentController = new AssessmentController(assessmentService);
    }

    @Test
    void testAddAssessment_Success() throws Exception {
        // Initialize
        AssessmentDTO createdAssessment = new AssessmentDTO(assessmentId, assessmentContent);
        when(assessmentService.addAssessment(any(AssessmentDTO.class))).thenReturn(createdAssessment);

        // Testing
        mockMvc.perform(post("/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assessmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assessmentId").value(assessmentId));
    }

    @Test
    void testAddAssessment_ValidationException() throws Exception {
        // Arrange
        doThrow(new ValidationException("Assessment with ID 1 already exists."))
                .when(assessmentService).addAssessment(any(AssessmentDTO.class));

        // Act & Assert
        mockMvc.perform(post("/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assessmentDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void testViewAssessment_Success() throws Exception {
        when(assessmentService.viewAssessment(assessmentId)).thenReturn(assessmentDTO);

        mockMvc.perform(get("/assessments/{assessmentId}", assessmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assessmentId").value(assessmentId));
    }

    @Test
    void testViewAssessment_ResourceNotFoundException() throws Exception {
        // Arrange
        when(assessmentService.viewAssessment(assessmentId)).thenThrow(new ResourceNotFoundException("Assessment not found with ID: " + assessmentId));

        // Act & Assert
        mockMvc.perform(get("/assessments/{assessmentId}", assessmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAssessment_Success() throws Exception {
        // Initialize
        AssessmentDTO updatedAssessment = new AssessmentDTO(assessmentId, "Updated Content");

        when(assessmentService.updateAssessment(any(AssessmentDTO.class))).thenReturn(updatedAssessment);

        mockMvc.perform(put("/assessments/{assessmentId}", assessmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assessmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assessmentId").value(assessmentId));
    }

    @Test
    void testUpdateAssessment_ResourceNotFoundException() throws Exception {
        assessmentDTO.setAssessmentId(2);

        when(assessmentService.updateAssessment(any(AssessmentDTO.class))).thenThrow(new ResourceNotFoundException("Assessment not found with ID: 2"));

        mockMvc.perform(put("/assessments/{assessmentId}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assessmentDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAssessment_Success() throws Exception {
        doNothing().when(assessmentService).deleteAssessment(assessmentId);

        // Perform the delete request
        mockMvc.perform(delete("/assessments/{assessmentId}", assessmentId))
                .andExpect(status().isNoContent());

        // Verify the service method was called with the correct parameter
        verify(assessmentService).deleteAssessment(assessmentId);
    }

    @Test
    void testDeleteAssessment_ResourceNotFoundException() throws Exception {
        // Mock the service to throw ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Assessment not found with ID: 2"))
                .when(assessmentService).deleteAssessment(2);

        // Perform the delete request and expect NOT_FOUND status
        mockMvc.perform(delete("/assessments/{assessmentId}", 2))
                .andExpect(status().isNotFound());

        // Verify the service method was called with the correct parameter
        verify(assessmentService).deleteAssessment(2);
    }
}
