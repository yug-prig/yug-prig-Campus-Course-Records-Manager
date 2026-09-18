package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Instructor;
import edu.ccrm.exception.InstructorNotFoundException;
import java.util.List;

public class InstructorServiceImpl implements InstructorService {
    private final DataStore dataStore = DataStore.getInstance();

    @Override
    public void addInstructor(Instructor instructor) {
        dataStore.getInstructors().add(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) throws InstructorNotFoundException {
        return dataStore.getInstructors().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with ID '" + id + "' not found."));
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return dataStore.getInstructors();
    }
}