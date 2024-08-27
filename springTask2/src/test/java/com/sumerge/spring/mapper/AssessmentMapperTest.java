package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring3.classes.Assessment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentMapperTest {

    private final AssessmentMapper assessmentMapper = AssessmentMapper.INSTANCE;

    @Test
    public void testMapToAssessmentDTO() {

        Assessment assessment = new Assessment(1, "Sample Assessment Content");

        AssessmentDTO assessmentDTO = assessmentMapper.mapToAssessmentDTO(assessment);

        assertEquals(assessment.getAssessmentId(), assessmentDTO.getAssessmentId());
        assertEquals(assessment.getAssessmentContent(), assessmentDTO.getAssessmentContent());
    }

    @Test
    public void testMapToAssessment() {

        AssessmentDTO assessmentDTO = new AssessmentDTO(1, "Sample Assessment Content");

        Assessment assessment = assessmentMapper.mapToAssessment(assessmentDTO);

        assertEquals(assessmentDTO.getAssessmentId(), assessment.getAssessmentId());
        assertEquals(assessmentDTO.getAssessmentContent(), assessment.getAssessmentContent());
    }
}
