package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring3.classes.Assessment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentMapperTest {

    private final AssessmentMapper assessmentMapper = AssessmentMapper.INSTANCE;

    @Test
    public void testMapToAssessmentDTO() {
        // Given
        Assessment assessment = new Assessment(1, "Sample Assessment Content");

        // When
        AssessmentDTO assessmentDTO = assessmentMapper.mapToAssessmentDTO(assessment);

        // Then
        assertEquals(assessment.getAssessmentId(), assessmentDTO.getAssessmentId());
        assertEquals(assessment.getAssessmentContent(), assessmentDTO.getAssessmentContent());
    }

    @Test
    public void testMapToAssessment() {
        // Given
        AssessmentDTO assessmentDTO = new AssessmentDTO(1, "Sample Assessment Content");

        // When
        Assessment assessment = assessmentMapper.mapToAssessment(assessmentDTO);

        // Then
        assertEquals(assessmentDTO.getAssessmentId(), assessment.getAssessmentId());
        assertEquals(assessmentDTO.getAssessmentContent(), assessment.getAssessmentContent());
    }
}
