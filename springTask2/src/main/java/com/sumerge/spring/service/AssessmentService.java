package com.sumerge.spring.service;

import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.AssessmentMapper;
import com.sumerge.spring.repository.AssessmentRepository;
import com.sumerge.spring3.classes.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, AssessmentMapper assessmentMapper) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentMapper = assessmentMapper;
    }

    // Add Assessment
    public AssessmentDTO addAssessment(AssessmentDTO assessmentDTO) throws ValidationException {
        int assessmentDTOCourseId = assessmentDTO.getAssessmentId();
        int assessmentId = assessmentDTO.getAssessmentId();

        // Check if a rating already exists for the given courseId and ratingId
        if (assessmentRepository.findById(assessmentId).isPresent()) {
            throw new ValidationException("Assessment with ID " + assessmentId + " already exists.");
        }

        Assessment assessment = assessmentMapper.mapToAssessment(assessmentDTO);
        Assessment savedAssessment = assessmentRepository.save(assessment);
        return assessmentMapper.mapToAssessmentDTO(savedAssessment);
    }

    // Update Assessment
    public AssessmentDTO updateAssessment(AssessmentDTO assessmentDTO) throws ResourceNotFoundException {
        int assessmentId = assessmentDTO.getAssessmentId();
        if (!assessmentRepository.existsById(assessmentId)) {
            throw new ResourceNotFoundException("Assessment with ID " + assessmentId + " not found.");
        }
        Assessment assessment = assessmentMapper.mapToAssessment(assessmentDTO);
        Assessment updatedAssessment = assessmentRepository.save(assessment);
        return assessmentMapper.mapToAssessmentDTO(updatedAssessment);
    }

    // Delete Assessment
    public void deleteAssessment(int assessmentId) throws ResourceNotFoundException {
        if (!assessmentRepository.existsById(assessmentId)) {
            throw new ResourceNotFoundException("Assessment with ID " + assessmentId + " not found.");
        }
        assessmentRepository.deleteById(assessmentId);
    }

    // View Assessment by ID
    public AssessmentDTO viewAssessment(int assessmentId) throws ResourceNotFoundException{
        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(() -> new ResourceNotFoundException("Assessment not found with id: "+ assessmentId));
        return assessmentMapper.mapToAssessmentDTO(assessment);
    }
}
