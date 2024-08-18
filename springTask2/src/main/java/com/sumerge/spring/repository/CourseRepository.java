package com.sumerge.spring.repository;

import com.sumerge.spring3.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// extends JPA automatically provides implementations for common database operations, so no need for CourseRepositoryImpl
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
