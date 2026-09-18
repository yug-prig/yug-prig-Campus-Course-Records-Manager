package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor; // 1. Add new import
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.InstructorNotFoundException; // 2. Add new import

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private final DataStore dataStore = DataStore.getInstance();
    // 3. Add an instance of InstructorService to find instructors
    private final InstructorService instructorService = new InstructorServiceImpl();

    @Override
    public void addCourse(Course course) {
        dataStore.getCourses().add(course);
    }

    @Override
    public Course findCourseByCode(String code) throws CourseNotFoundException {
        // Using Stream API to find a course
        return dataStore.getCourses().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course with code '" + code + "' not found."));
    }

    @Override
    public List<Course> getAllCourses() {
        return dataStore.getCourses();
    }

    @Override
    public List<Course> findCoursesByDepartment(String department) {
        // Lambda expression used as a predicate for filtering
        return dataStore.getCourses().stream()
                .filter(course -> course.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // 4. Implement the new method from the interface
    @Override
    public void assignInstructorToCourse(String courseCode, int instructorId) throws CourseNotFoundException, InstructorNotFoundException {
        // Find the course using an existing method
        Course course = findCourseByCode(courseCode);
        
        // Find the instructor using the new InstructorService
        Instructor instructor = instructorService.findInstructorById(instructorId);
        
        // Assign the found instructor to the course
        course.assignInstructor(instructor);
        
        System.out.println("Successfully assigned " + instructor.getFullName() + " to " + course.getTitle());
    }
}