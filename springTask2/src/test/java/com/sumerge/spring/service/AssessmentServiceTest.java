package com.sumerge.spring.service;

import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.AssessmentMapper;
import com.sumerge.spring.repository.AssessmentRepository;
import com.sumerge.spring3.classes.Assessment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssessmentServiceTest {
    // mocks
    @Mock
    private AssessmentMapper assessmentMapper;
    @Mock
    private AssessmentRepository assessmentRepository;
    @InjectMocks
    private AssessmentService assessmentService;

    // initialize
    private Assessment assessment;
    private Assessment savedAssessment;
    private AssessmentDTO assessmentDTO;
    private int assessmentId = 1;
    private String assessmentContent = "Test Assessment Content";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        assessmentDTO = new AssessmentDTO(assessmentId, assessmentContent);
        assessment = new Assessment();
        savedAssessment = new Assessment();
        // assign id
        savedAssessment.setAssessmentId(assessmentId);
    }

    @Test
    void testAddAssessment_Success() throws ValidationException {
        // testing
        when(assessmentRepository.findById(assessmentDTO.getAssessmentId())).thenReturn(Optional.empty());
        when(assessmentMapper.mapToAssessment(assessmentDTO)).thenReturn(assessment);
        when(assessmentRepository.save(assessment)).thenReturn(savedAssessment);
        when(assessmentMapper.mapToAssessmentDTO(savedAssessment)).thenReturn(assessmentDTO);

        // check results
        AssessmentDTO result = assessmentService.addAssessment(assessmentDTO);

        assertNotNull(result, "The result should not be null");
        assertEquals(assessmentDTO.getAssessmentId(), result.getAssessmentId());
        assertEquals(assessmentDTO.getAssessmentContent(), result.getAssessmentContent());
    }

    @Test
    void testAddAssessment_ValidationException(){
        when(assessmentRepository.findById(assessmentDTO.getAssessmentId())).thenReturn(Optional.of(new Assessment()));

        // exception
        ValidationException exception = assertThrows(ValidationException.class, () -> assessmentService.addAssessment(assessmentDTO));

        // check results
        assertEquals("Assessment with ID 1 already exists.", exception.getMessage());
    }

    @Test
    void testViewAssessment_Success() throws ResourceNotFoundException {
        //initialize
        assessment.setAssessmentId(assessmentId);
        assessment.setAssessmentContent(assessmentContent);

        // arrange
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));
        // mapping
        when(assessmentMapper.mapToAssessmentDTO(assessment)).thenReturn(assessmentDTO);

        // check results
        AssessmentDTO result = assessmentService.viewAssessment(assessmentId);

        assertNotNull(result, "The result should not be null");
        assertEquals(assessmentId, result.getAssessmentId());
        assertEquals(assessmentContent, result.getAssessmentContent());
    }

    @Test
    void testViewAssessment_ResourceNotFoundException(){
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> assessmentService.viewAssessment(assessmentId));

        assertEquals("Assessment not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateAssessment_Success() throws ResourceNotFoundException {
        // Initialize
        assessment.setAssessmentId(assessmentId);
        assessment.setAssessmentContent(assessmentContent);
        assessmentDTO.setAssessmentContent("Updated Content");

        // Mocking
        when(assessmentRepository.existsById(assessmentId)).thenReturn(true); // Ensure existsById returns true
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment)); // Return existing assessment
        when(assessmentMapper.mapToAssessment(assessmentDTO)).thenReturn(assessment); // Map to Assessment
        when(assessmentRepository.save(assessment)).thenReturn(assessment); // Save and return Assessment
        when(assessmentMapper.mapToAssessmentDTO(assessment)).thenReturn(assessmentDTO); // Map to AssessmentDTO

        // Execution
        AssessmentDTO result = assessmentService.updateAssessment(assessmentDTO);

        // Check results
        assertNotNull(result, "The result should not be null");
        assertEquals(assessmentId, result.getAssessmentId());
        assertEquals("Updated Content", result.getAssessmentContent());
    }

    @Test
    void testUpdateAssessment_ResourceNotFoundException(){
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> assessmentService.updateAssessment(assessmentDTO));

        assertEquals("Assessment with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testDeleteAssessment_Success() throws ResourceNotFoundException {
        when(assessmentRepository.existsById(assessmentId)).thenReturn(true);

        // Act
        assessmentService.deleteAssessment(assessmentId);

        // Assert
        verify(assessmentRepository, times(1)).deleteById(assessmentId);
    }

    @Test
    void testDeleteAssessment_ResourceNotFoundException(){
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> assessmentService.deleteAssessment(assessmentId));

        assertEquals("Assessment with ID 1 not found.", exception.getMessage());
    }
}
