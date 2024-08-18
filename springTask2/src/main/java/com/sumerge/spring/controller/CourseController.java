package com.sumerge.spring.controller;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.service.CourseService;
import com.sumerge.spring3.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CourseDTO addCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.addCourse(courseDTO);
    }

    // view course by id --> get mapping
    @GetMapping("/{courseId}")
    public CourseDTO viewCourse(@PathVariable int courseId) {
        return courseService.viewCourse(courseId);
    }

    // update course --> put mapping
    @PutMapping("/{courseId}")
    public CourseDTO updateCourse(@PathVariable int courseId, @RequestBody CourseDTO courseDTO) {
        courseDTO.setCourseId(courseId);
        return courseService.updateCourse(courseDTO);
    }

    // delete course by id --> delete mapping
    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
    }

    // view all courses using paging -- > get mapping
    @GetMapping
    public Page<CourseDTO> viewAllCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return courseService.viewAllCourses(page, size);
    }
}
