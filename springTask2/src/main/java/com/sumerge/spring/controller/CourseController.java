package com.sumerge.spring.controller;

import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    // Bean constructor
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // add course --> post mapping
    @PostMapping
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO createdCourse = courseService.addCourse(courseDTO);
            return new ResponseEntity<>(createdCourse, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // view course by id --> get mapping
    @GetMapping("/{courseName}")
    public ResponseEntity<CourseDTO> viewCourse(@PathVariable String courseName) {
        try{
            CourseDTO course = courseService.viewCourse(courseName);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update course --> put mapping
    @PutMapping("/{courseName}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String courseName, @RequestBody CourseDTO courseDTO) {
        try{
            courseDTO.setCourseName(courseName);
            CourseDTO updatedCourse = courseService.updateCourse(courseDTO);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch( ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete course by id --> delete mapping
    @DeleteMapping("/{courseName}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseName) {
        try{
            courseService.deleteCourse(courseName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // view all courses using paging -- > get mapping
    public ResponseEntity<Page<CourseDTO>> viewAllCourses(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<CourseDTO> authors = courseService.viewAllCourses(page, size);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
}
