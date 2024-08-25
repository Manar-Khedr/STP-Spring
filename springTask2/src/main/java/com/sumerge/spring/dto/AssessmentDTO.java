package com.sumerge.spring.dto;

public class AssessmentDTO {

    private int assessmentId;
    private String assessmentContent;

    public AssessmentDTO(int assessmentId, String assessmentContent){
        this.assessmentContent = assessmentContent;
        this.assessmentId = assessmentId;
    }

    public AssessmentDTO(){}


    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentContent() {
        return assessmentContent;
    }

    public void setAssessmentContent(String assessmentContent) {
        this.assessmentContent = assessmentContent;
    }
}
