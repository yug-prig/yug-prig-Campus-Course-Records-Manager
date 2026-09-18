package edu.ccrm.domain;

import java.time.LocalDate;

public class Student extends Person {
    private final String regNo;

    public Student(int id, String regNo, String fullName, String email, LocalDate dateOfBirth) {
        // 'super' keyword calls the parent class constructor
        super(id, fullName, email, dateOfBirth);
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    @Override
    public String getDetailedProfile() {
        return String.format("--- Student Profile ---\nRegistration No: %s\n%s", regNo, super.toString());
    }
}