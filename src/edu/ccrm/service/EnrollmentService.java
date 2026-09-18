package edu.ccrm.service;

import edu.ccrm.domain.Grade;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.StudentNotFoundException;

public interface EnrollmentService {
    void enrollStudent(String regNo, String courseCode) throws StudentNotFoundException, CourseNotFoundException;
    void assignGrade(String regNo, String courseCode, Grade grade) throws StudentNotFoundException, CourseNotFoundException;
    void printTranscript(String regNo) throws StudentNotFoundException;
}