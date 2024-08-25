package com.sumerge.spring.repository;

import com.sumerge.spring3.classes.Assessment;
import com.sumerge.spring3.classes.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
}
