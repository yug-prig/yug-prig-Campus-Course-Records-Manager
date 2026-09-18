package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.exception.StudentNotFoundException;
import java.util.List;

public interface StudentService {
    void addStudent(Student student);
    Student findStudentByRegNo(String regNo) throws StudentNotFoundException;
    List<Student> getAllStudents();
}