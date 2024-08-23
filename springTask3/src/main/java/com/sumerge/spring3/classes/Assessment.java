package com.sumerge.spring3.classes;

import javax.persistence.*;

@Entity
@Table(name = "assessment")
public class Assessment {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assessmentId;

    private String assessmentContent;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Assessment(int assessmentId, String assessmentContent){
        this.assessmentId = assessmentId;
        this.assessmentContent = assessmentContent;
    }

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

