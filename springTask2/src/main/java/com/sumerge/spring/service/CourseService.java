package com.sumerge.spring.service;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.CourseRecommendationException;
import com.sumerge.spring.exception.RecommendationUnavailableException;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.CourseMapper;
import com.sumerge.spring.repository.CourseRepository;
import com.sumerge.spring3.CourseRecommender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sumerge.spring3.classes.Course;

import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    //private final CourseRecommender courseRecommender;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Autowired
    @Qualifier("soapCourseRecommender")
    private final CourseRecommender courseRecommender;


    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, CourseRecommender courseRecommender,
                         CourseRecommender soapRecommender) {
        //this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseRecommender = courseRecommender;
    }



    // Add course
    public CourseDTO addCourse(CourseDTO courseDTO) throws ValidationException{
        String courseName = courseDTO.getCourseName();
        if (courseRepository.findByCourseName(courseName).isPresent()) {
            throw new ValidationException("Course with name " + courseName + " already exists.");
        }
        Course course = courseMapper.mapToCourse(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.mapToCourseDTO(savedCourse);
    }

    // Update course
    public CourseDTO updateCourse(CourseDTO courseDTO) throws ResourceNotFoundException {
        String courseName = courseDTO.getCourseName();
        Course existingCourse = courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with name: " + courseName));;

        // Update only the necessary fields
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setCourseDescription(courseDTO.getCourseDescription());
        existingCourse.setCourseCredit(courseDTO.getCourseCredit());
        // Update other fields as needed

        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.mapToCourseDTO(updatedCourse);
    }

    // Delete course
    public void deleteCourse(String courseName) throws ResourceNotFoundException{
        Course course = courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with name: " + courseName));
        courseRepository.delete(course);
    }

    // View course
    public CourseDTO viewCourse(String courseName) throws ResourceNotFoundException {
        Course course = courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with name: " + courseName));
        return courseMapper.mapToCourseDTO(course);
    }

    // View all courses with pagination
    public Page<CourseDTO> viewAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable)
                .map(courseMapper::mapToCourseDTO);
    }

    public List<Course> getRecommendedCourses() {
        try {
            return courseRecommender.recommendedCourses();
        } catch (CourseRecommendationException e) {
            logger.error("Error getting recommended courses", e);
            throw new RecommendationUnavailableException("Failed to fetch recommended courses", e);
        }
    }
}