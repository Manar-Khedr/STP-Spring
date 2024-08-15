package com.sumerge.spring.controller;

import com.sumerge.spring.CourseService;
import com.sumerge.spring3.Course;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Get mapping --> get course from database
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable int courseId) {
        Course course = courseService.viewCourse(courseId);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    // 2. Add a course
    @PostMapping
    public ResponseEntity<Void> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return ResponseEntity.ok().build();
    }

    // 3. Update a certain course
    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable int courseId, @RequestBody Course course) {
        Course choosenCourse = courseService.viewCourse(courseId);
        if (choosenCourse != null) {
            course.setId(courseId);
            courseService.updateCourse(course);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 4. Delete a certain course
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        Course choosenCourse = courseService.viewCourse(courseId);
        if (choosenCourse != null) {
            courseService.deleteCourse(choosenCourse);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 5. View all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.viewAllCourses();
        if (courses != null && !courses.isEmpty()) {
            return ResponseEntity.ok(courses);
        }
        return ResponseEntity.notFound().build();
    }

}
