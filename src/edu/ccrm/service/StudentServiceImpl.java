package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.StudentNotFoundException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final DataStore dataStore = DataStore.getInstance();

    @Override
    public void addStudent(Student student) {
        dataStore.getStudents().add(student);
    }

    @Override
    public Student findStudentByRegNo(String regNo) throws StudentNotFoundException {
        return dataStore.getStudents().stream()
                .filter(s -> s.getRegNo().equalsIgnoreCase(regNo))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student with RegNo '" + regNo + "' not found."));
    }

    @Override
    public List<Student> getAllStudents() {
        return dataStore.getStudents();
    }
}