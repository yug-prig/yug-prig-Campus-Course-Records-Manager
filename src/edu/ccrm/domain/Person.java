package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    // Encapsulation: fields are private
    protected int id;
    protected String fullName;
    protected String email;
    protected LocalDate dateOfBirth;

    public Person(int id, String fullName, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Abstract method to be implemented by subclasses (demonstrates Polymorphism)
    public abstract String getDetailedProfile();

    // Getters and Setters
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    @Override
    public String toString() {
        return "ID=" + id + ", Name='" + fullName + "', Email='" + email + "'";
    }
}