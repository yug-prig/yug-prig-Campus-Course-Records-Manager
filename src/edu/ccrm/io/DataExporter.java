package edu.ccrm.io;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DataExporter {
    private static final Path EXPORT_DIR = Paths.get("data", "exports");
    private final DataStore dataStore = DataStore.getInstance();

    public void exportAllData() throws IOException {
        // Ensure the export directory exists
        Files.createDirectories(EXPORT_DIR);

        exportStudents();
        exportCourses();
        exportEnrollments();

        System.out.println("All data successfully exported to: " + EXPORT_DIR.toAbsolutePath());
    }

    private void exportStudents() throws IOException {
        Path studentFile = EXPORT_DIR.resolve("students.csv");
        List<String> lines = dataStore.getStudents().stream()
                .map(s -> String.join(",",
                        String.valueOf(s.getId()),
                        s.getRegNo(),
                        s.getFullName(),
                        s.getEmail(),
                        s.getDateOfBirth().toString()))
                .collect(Collectors.toList());

        // Add a header row
        lines.add(0, "id,regNo,fullName,email,dob");
        Files.write(studentFile, lines);
    }

    private void exportCourses() throws IOException {
        Path courseFile = EXPORT_DIR.resolve("courses.csv");
        List<String> lines = dataStore.getCourses().stream()
                .map(c -> String.join(",",
                        c.getCode(),
                        c.getTitle(),
                        String.valueOf(c.getCredits()),
                        c.getDepartment(),
                        c.getSemester().name()))
                .collect(Collectors.toList());
        lines.add(0, "code,title,credits,department,semester");
        Files.write(courseFile, lines);
    }

    private void exportEnrollments() throws IOException {
        Path enrollmentFile = EXPORT_DIR.resolve("enrollments.csv");
        List<String> lines = dataStore.getEnrollments().stream()
                .map(e -> String.join(",",
                        e.getStudent().getRegNo(),
                        e.getCourse().getCode(),
                        e.getGrade() != null ? e.getGrade().name() : "N/A"))
                .collect(Collectors.toList());
        lines.add(0, "studentRegNo,courseCode,grade");
        Files.write(enrollmentFile, lines);
    }
}