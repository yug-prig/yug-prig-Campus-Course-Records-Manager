package edu.ccrm.domain;

import java.time.LocalDate;

public class Instructor extends Person {
    private String department;

    public Instructor(int id, String fullName, String email, LocalDate dateOfBirth, String department) {
        super(id, fullName, email, dateOfBirth);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getDetailedProfile() {
        return String.format("--- Instructor Profile ---\nDepartment: %s\n%s", department, super.toString());
    }
}