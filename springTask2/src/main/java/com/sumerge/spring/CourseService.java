package com.sumerge.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.Course;

import java.util.List;

public class CourseService {

    private CourseRecommender courseRecommender;
    private JdbcTemplate jdbcTemplate;


    // JDBC Template Constructor
    @Autowired
    public CourseService(JdbcTemplate jdbcTemplate, CourseRecommender courseRecommender){
        this.jdbcTemplate = jdbcTemplate;
        this.courseRecommender = courseRecommender;
    }

    public List<Course> getRecommendedCourses() {
        return courseRecommender.recommendedCourses();
    }

    // PostgreSQL

    public void addCourse(Course course){
        // name, id, description, credit
        String sql = "INSERT INTO Course(id,name,description,credit) VALUES(?,?,?,?)";
        // USE UPDATE FOR INSERT SINCE THERE IS NO INSERT FUNCTION
        jdbcTemplate.update(sql, course.getId(), course.getName(), course.getDescription(), course.getCredit());
    }

    public void updateCourse(Course course){
        String sql = "UPDATE Course SET name = ? , description = ?, credit = ? WHERE id = ?";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getCredit(), course.getId());
    }

    public void deleteCourse(Course course){
        String sql = "DELETE FROM Course WHERE id = ?";
        jdbcTemplate.update(sql, course.getId());
    }

    // USE queryForObject to map directly to Course Type
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
            // Return null if no course is found with the given id
            return null;
        }
    }


}
