package com.sumerge.spring.controller;

import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<AssessmentDTO> addAssessment(@RequestBody AssessmentDTO assessmentDTO) throws ValidationException {
        try {
            AssessmentDTO createdAssessment = assessmentService.addAssessment(assessmentDTO);
            return new ResponseEntity<>(createdAssessment, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDTO> updateAssessment(@PathVariable int assessmentId, @RequestBody AssessmentDTO assessmentDTO) throws ResourceNotFoundException {
        try {
            assessmentDTO.setAssessmentId(assessmentId);
            AssessmentDTO updatedAssessment = assessmentService.updateAssessment(assessmentDTO);
            return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable int assessmentId) throws ResourceNotFoundException {
        try {
            assessmentService.deleteAssessment(assessmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDTO> viewAssessment(@PathVariable int assessmentId) throws ResourceNotFoundException {
        try {
            AssessmentDTO assessment = assessmentService.viewAssessment(assessmentId);
            return new ResponseEntity<>(assessment, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
