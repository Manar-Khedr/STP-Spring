package com.sumerge.spring.controller;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.RecommendationUnavailableException;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.impl.SoapCourseRecommender;
import com.sumerge.spring.service.CourseService;
import com.sumerge.spring.service.SecurityService;
import com.sumerge.spring3.classes.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import javax.validation.ValidationException;
import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(SoapCourseRecommender.class);

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.isAdmin()")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO createdCourse = courseService.addCourse(courseDTO);
            return new ResponseEntity<>(createdCourse, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{courseName}")
    public ResponseEntity<CourseDTO> viewCourse(@PathVariable String courseName) {
        try {
            CourseDTO course = courseService.viewCourse(courseName);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{courseName}")
    @PreAuthorize("@securityService.isAdmin()")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String courseName, @RequestBody CourseDTO courseDTO) {
        try {
            courseDTO.setCourseName(courseName);
            CourseDTO updatedCourse = courseService.updateCourse(courseDTO);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{courseName}")
    @PreAuthorize("@securityService.isAdmin()")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseName) {
        try {
            courseService.deleteCourse(courseName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/discover")
//    public ResponseEntity<Page<CourseDTO>> viewAllCourses(@RequestParam(defaultValue = "0") int page,
//                                                          @RequestParam(defaultValue = "10") int size) {
//        Page<CourseDTO> courses = courseService.viewAllCourses(page, size);
//        return new ResponseEntity<>(courses, HttpStatus.OK);
//    }


//    private Page<CourseDTO> convertToPage(List<CourseDTO> courses, int page, int size) {
//        int start = Math.min((int) PageRequest.of(page, size).getOffset(), courses.size());
//        int end = Math.min((start + size), courses.size());
//        return new PageImpl<>(courses.subList(start, end), PageRequest.of(page, size), courses.size());
//    }


    @GetMapping("/discover")
    public ResponseEntity<List<Course>> discoverCourses() {
        try {
            List<Course> recommendedCourses = courseService.getRecommendedCourses();
            return ResponseEntity.ok(recommendedCourses);
        } catch (RecommendationUnavailableException e) {
            logger.error("Failed to fetch recommended courses", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Collections.emptyList());
        } catch (Exception e) {
            logger.error("Unexpected error in getting recommended courses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

}