package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.exception.StudentNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentServiceImpl implements EnrollmentService {
    private static final int MAX_CREDITS_PER_SEMESTER = 18;
    private final DataStore dataStore = DataStore.getInstance();
    private final StudentService studentService = new StudentServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public void enrollStudent(String regNo, String courseCode) throws StudentNotFoundException, CourseNotFoundException {
        Student student = studentService.findStudentByRegNo(regNo);
        Course course = courseService.findCourseByCode(courseCode);

        // Business Rule: Check for duplicate enrollment
        boolean alreadyEnrolled = dataStore.getEnrollments().stream()
                .anyMatch(e -> e.getStudent().equals(student) && e.getCourse().equals(course));

        if (alreadyEnrolled) {
            System.out.println("Warning: Student is already enrolled in this course.");
            return;
        }

        // Business Rule: Check for max credit limit
        int currentCredits = dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student))
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException(
                "Cannot enroll. Student would exceed the max credit limit of " + MAX_CREDITS_PER_SEMESTER
            );
        }

        dataStore.getEnrollments().add(new Enrollment(student, course));
        System.out.println("Enrollment successful!");
    }

    @Override
    public void assignGrade(String regNo, String courseCode, Grade grade) throws StudentNotFoundException, CourseNotFoundException {
        Student student = studentService.findStudentByRegNo(regNo);
        Course course = courseService.findCourseByCode(courseCode);

        dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student) && e.getCourse().equals(course))
                .findFirst()
                .ifPresentOrElse(
                    enrollment -> {
                        enrollment.setGrade(grade);
                        System.out.println("Grade assigned successfully.");
                    },
                    () -> System.out.println("Error: No matching enrollment found to assign grade.")
                );
    }

    @Override
    public void printTranscript(String regNo) throws StudentNotFoundException {
        Student student = studentService.findStudentByRegNo(regNo);

        System.out.println("\n--- Academic Transcript ---");
        System.out.println(student.getDetailedProfile());
        System.out.println("---------------------------");

        List<Enrollment> studentEnrollments = dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());
        
        if (studentEnrollments.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        double totalGradePoints = 0;
        int totalCredits = 0;

        System.out.printf("%-10s %-30s %-8s %-10s\n", "Course", "Title", "Credits", "Grade");
        System.out.println("---------------------------------------------------------------");
        
        for (Enrollment e : studentEnrollments) {
            Course c = e.getCourse();
            Grade g = e.getGrade();
            String gradeStr = (g != null) ? g.name() : "IP"; // IP for In Progress

            System.out.printf("%-10s %-30s %-8d %-10s\n", c.getCode(), c.getTitle(), c.getCredits(), gradeStr);
            
            if (g != null) {
                totalGradePoints += g.getGradePoint() * c.getCredits();
                totalCredits += c.getCredits();
            }
        }
        
        System.out.println("---------------------------------------------------------------");
        if (totalCredits > 0) {
            double gpa = totalGradePoints / totalCredits;
            System.out.printf("Cumulative GPA: %.2f\n", gpa);
        } else {
            System.out.println("Cumulative GPA: N/A (No graded courses)");
        }
    }
}