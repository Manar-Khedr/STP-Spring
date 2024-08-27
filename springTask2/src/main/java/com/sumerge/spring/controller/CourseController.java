package com.sumerge.spring.controller;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.CourseService;
import com.sumerge.spring.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/courses")
public class CourseController {

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

    @GetMapping("/discover")
    public ResponseEntity<Page<CourseDTO>> viewAllCourses(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<CourseDTO> courses = courseService.viewAllCourses(page, size);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}