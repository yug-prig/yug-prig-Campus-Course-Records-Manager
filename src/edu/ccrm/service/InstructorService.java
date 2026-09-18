package edu.ccrm.service;

import edu.ccrm.domain.Instructor;
import edu.ccrm.exception.InstructorNotFoundException;
import java.util.List;

public interface InstructorService {
    void addInstructor(Instructor instructor);
    Instructor findInstructorById(int id) throws InstructorNotFoundException;
    List<Instructor> getAllInstructors();
}