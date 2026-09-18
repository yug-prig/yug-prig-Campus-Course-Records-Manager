package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.InstructorNotFoundException; // 1. Import the new exception

import java.util.List;

public interface CourseService {
    void addCourse(Course course);

    Course findCourseByCode(String code) throws CourseNotFoundException;

    List<Course> getAllCourses();

    List<Course> findCoursesByDepartment(String department);

    // 2. Add this new method signature for assigning an instructor
    void assignInstructorToCourse(String courseCode, int instructorId) throws CourseNotFoundException, InstructorNotFoundException;
}