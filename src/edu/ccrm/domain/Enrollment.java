package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDateTime enrollmentDate;
    private Grade grade;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now(); // Using Date/Time API
        this.grade = null; // Grade is null until assigned
    }

    // Getters and a setter for the grade
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("Enrollment[Student: %s, Course: %s, Grade: %s]",
                student.getFullName(), course.getCode(), (grade != null ? grade : "Not Graded"));
    }
}