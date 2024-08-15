package com.sumerge.spring;

import com.sumerge.spring3.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository

public class CourseRepositoryImpl implements CourseRepository{

    private JdbcTemplate jdbcTemplate;

    // Constructor
    @Autowired
    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCourse(Course course) {
        // name, id, description, credit
        String sql = "INSERT INTO Course(id,name,description,credit) VALUES(?,?,?,?)";
        // USE UPDATE FOR INSERT SINCE THERE IS NO INSERT FUNCTION
        jdbcTemplate.update(sql, course.getId(), course.getName(), course.getDescription(), course.getCredit());
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE Course SET name = ? , description = ?, credit = ? WHERE id = ?";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getCredit(), course.getId());
    }

    @Override
    public void deleteCourse(Course course) {
        String sql = "DELETE FROM Course WHERE id = ?";
        jdbcTemplate.update(sql, course.getId());
    }

    @Override
    public Course viewCourse(int courseId) {
        String sql = "SELECT * FROM Course WHERE id = ?";

        try {
            // Query the database and map the result to a Course object
            return jdbcTemplate.queryForObject(sql, new Object[]{courseId}, (rs, rowNum) ->
                    new Course(
                            rs.getString("name"),
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getInt("credit")
                    ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Course> viewAllCourses() {
        String sql = "SELECT * FROM Course"; // Query to select all courses

        try {
            // Query the database and map the result to a list of Course objects
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Course(
                            rs.getString("name"),
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getInt("credit")
                    ));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}
