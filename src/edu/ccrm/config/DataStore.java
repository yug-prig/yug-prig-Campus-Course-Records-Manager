package edu.ccrm.config;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Instructor; // 1. Add this import statement
import edu.ccrm.domain.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStore {
    private static DataStore instance;

    private final List<Student> students;
    private final List<Course> courses;
    private final List<Enrollment> enrollments;
    private final List<Instructor> instructors;

    // Private constructor to prevent direct instantiation
    private DataStore() {
        // Using thread-safe lists is good practice, though not strictly required for this console app
        students = new CopyOnWriteArrayList<>();
        courses = new CopyOnWriteArrayList<>();
        enrollments = new CopyOnWriteArrayList<>();
        instructors = new CopyOnWriteArrayList<>();
    }

    // Public static method to get the single instance of the class
    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Getters for the data lists
    public List<Student> getStudents() { return students; }
    public List<Course> getCourses() { return courses; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public List<Instructor> getInstructors() { return instructors; } // 2. Add this getter method
}