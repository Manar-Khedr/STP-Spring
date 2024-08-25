package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.AssessmentDTO;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring3.classes.Assessment;
import com.sumerge.spring3.classes.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssessmentMapper {

    AssessmentMapper INSTANCE = Mappers.getMapper(AssessmentMapper.class);

    Assessment mapToAssessment(AssessmentDTO assessmentDTO);
    AssessmentDTO mapToAssessmentDTO(Assessment assessment);
}
