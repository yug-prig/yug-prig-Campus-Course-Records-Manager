package edu.ccrm.domain;

public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final String department;
    private final Semester semester;
    private Instructor instructor; // A course HAS-A an instructor

    // Private constructor to enforce creation via the Builder
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.department = builder.department;
        this.semester = builder.semester;
        this.instructor = builder.instructor;
    }

    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getDepartment() { return department; }
    public Semester getSemester() { return semester; }
    public Instructor getInstructor() { return instructor; }

    // A setter for instructor, as they can be assigned later
    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getFullName() : "TBD";
        return String.format("Course{Code='%s', Title='%s', Credits=%d, Dept='%s', Instructor='%s'}",
                code, title, credits, department, instructorName);
    }

    // --- Static Nested Builder Class ---
    public static class Builder {
        private final String code;
        private final String title;
        private final int credits;
        private String department = "General";
        private Semester semester = Semester.FALL;
        private Instructor instructor = null;

        public Builder(String code, String title, int credits) {
            this.code = code;
            this.title = title;
            this.credits = credits;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}