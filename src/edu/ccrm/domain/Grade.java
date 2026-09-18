package edu.ccrm.domain;

public enum Grade {
    S(10.0), // Outstanding
    A(9.0),  // Excellent
    B(8.0),  // Very Good
    C(7.0),  // Good
    D(6.0),  // Average
    E(5.0),  // Pass
    F(0.0);  // Fail

    private final double gradePoint;

    // Enum constructor
    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    // Getter for the grade point value
    public double getGradePoint() {
        return gradePoint;
    }
}