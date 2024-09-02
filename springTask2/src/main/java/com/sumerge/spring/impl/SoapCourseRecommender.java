package com.sumerge.spring.impl;

import com.example.courses.CourseType;
import com.example.courses.Courses;
import com.sumerge.spring.exception.CourseRecommendationException;
import com.sumerge.spring3.CourseRecommender;
import com.sumerge.spring3.classes.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;


public class SoapCourseRecommender implements CourseRecommender {

    private static final Logger logger = LoggerFactory.getLogger(SoapCourseRecommender.class);

    private final String SOAP_SERVICE_URL = "http://localhost:8088/mockCourseService";
    private final RestTemplate restTemplate;

    public SoapCourseRecommender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Course> recommendedCourses() {
        try {
            String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cor=\"http://www.example.com/courses\">"
                    + "<soapenv:Header/>"
                    + "<soapenv:Body>"
                    + "<cor:getRecommendedCoursesRequest/>"
                    + "</soapenv:Body>"
                    + "</soapenv:Envelope>";

            String response = restTemplate.postForObject(SOAP_SERVICE_URL, soapRequest, String.class);

            JAXBContext jaxbContext = JAXBContext.newInstance(Courses.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Courses courses = (Courses) unmarshaller.unmarshal(new StringReader(response));

            return courses.getCourse().stream()
                    .map(this::convertToCourse)
                    .collect(Collectors.toList());
        } catch (JAXBException e) {
            logger.error("Error parsing SOAP response: {}", e.getMessage(), e);
            throw new CourseRecommendationException("Error parsing SOAP response", e);
        } catch (ResourceAccessException e) {
            logger.error("Error accessing SOAP service: {}", e.getMessage(), e);
            throw new CourseRecommendationException("Error calling SOAP service", e);
        } catch (Exception e) {
            logger.error("Unexpected error in getting recommended courses: {}", e.getMessage(), e);
            throw new CourseRecommendationException("Unexpected error in getting recommended courses", e);
        }
    }

    private Course convertToCourse(CourseType courseType) {
        Course course = new Course();
        course.setCourseId(courseType.getCourseId());
        course.setCourseName(courseType.getCourseName());
        course.setCourseDescription(courseType.getCourseDescription());
        course.setCourseCredit(courseType.getCourseCredit());
        course.setCourseDuration(courseType.getCourseDuration());
        return course;
    }
}