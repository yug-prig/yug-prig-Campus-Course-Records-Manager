package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.InstructorNotFoundException; // 1. Add new import
import edu.ccrm.exception.StudentNotFoundException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.DataExporter;
import edu.ccrm.service.*;
import edu.ccrm.util.RecursiveFileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class CCRMApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final InstructorService instructorService = new InstructorServiceImpl(); // Already added, good!
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private static final BackupService backupService = new BackupService();
    private static final DataExporter dataExporter = new DataExporter();

    public static void main(String[] args) {
        System.out.println("🚀 Welcome to the Campus Course & Records Manager (CCRM) 🚀");
        loadSampleData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput();
            scanner.nextLine(); // Consume newline

            // 2. Update the main switch statement
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageInstructors(); // New option
                case 3 -> manageCourses();
                case 4 -> manageEnrollment();
                case 5 -> runReports();
                case 6 -> fileUtilities();
                case 7 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Thank you for using CCRM. Goodbye!");
        scanner.close();
    }

    // 3. Update the main menu printout
    private static void printMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Instructors"); // New
        System.out.println("3. Manage Courses");
        System.out.println("4. Manage Enrollment & Grades");
        System.out.println("5. Reports");
        System.out.println("6. File Utilities");
        System.out.println("7. Exit");
        System.out.print("Select an option: ");
    }

    // 4. Add the new method to manage instructors
    private static void manageInstructors() {
        System.out.println("\n--- Instructor Management ---");
        System.out.println("1. Add New Instructor");
        System.out.println("2. View All Instructors");
        System.out.print("Select an option: ");
        int choice = getIntInput();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter ID: ");
            int id = getIntInput(); scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Department: ");
            String dept = scanner.nextLine();
            instructorService.addInstructor(new Instructor(id, name, email, LocalDate.now(), dept));
            System.out.println("Instructor added successfully!");
        } else if (choice == 2) {
            System.out.println("\nListing all instructors:");
            instructorService.getAllInstructors().forEach(i -> System.out.println(i.getDetailedProfile()));
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    // ... manageStudents() method remains the same ...
    private static void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.print("Select an option: ");
        int choice = getIntInput();
        scanner.nextLine();

        switch(choice) {
            case 1:
                System.out.print("Enter ID: ");
                int id = getIntInput(); scanner.nextLine();
                System.out.print("Enter Registration No: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                studentService.addStudent(new Student(id, regNo, name, email, LocalDate.of(2005, 5, 20)));
                System.out.println("Student added successfully!");
                break;
            case 2:
                System.out.println("\nListing all students:");
                studentService.getAllStudents().forEach(s -> System.out.println(s.getDetailedProfile()));
                break;
            default: System.out.println("Invalid choice.");
        }
    }


    // 5. Completely update the course management method
    private static void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Assign Instructor to Course"); // New Option
        System.out.print("Select an option: ");
        int choice = getIntInput();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter Course Code: ");
                String code = scanner.nextLine();
                System.out.print("Enter Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Credits: ");
                int credits = getIntInput();
                scanner.nextLine();
                System.out.print("Enter Department: ");
                String dept = scanner.nextLine();
                Course course = new Course.Builder(code, title, credits).department(dept).build();
                courseService.addCourse(course);
                System.out.println("Course added successfully!");
                break;
            case 2:
                System.out.println("\nListing all courses:");
                courseService.getAllCourses().forEach(System.out::println);
                break;
            case 3:
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                System.out.print("Enter Instructor ID to assign: ");
                int instructorId = getIntInput();
                scanner.nextLine();
                try {
                    courseService.assignInstructorToCourse(courseCode, instructorId);
                } catch (CourseNotFoundException | InstructorNotFoundException e) {
                    System.err.println("Error: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    // ... other methods (manageEnrollment, runReports, etc.) remain the same ...
    
    private static void manageEnrollment() {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Assign Grade to Student");
        System.out.print("Select an option: ");
        int choice = getIntInput();
        scanner.nextLine();

        try {
            if (choice == 1) {
                System.out.print("Enter Student Registration No: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                enrollmentService.enrollStudent(regNo, courseCode);
            } else if (choice == 2) {
                System.out.print("Enter Student Registration No: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
                Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());
                enrollmentService.assignGrade(regNo, courseCode, grade);
            } else {
                 System.out.println("Invalid choice.");
            }
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid grade entered.");
        } catch (RuntimeException e) {
             System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void runReports() {
        System.out.println("\n--- Reports ---");
        System.out.print("Enter Student Registration No to generate transcript: ");
        String regNo = scanner.nextLine();
        try {
            enrollmentService.printTranscript(regNo);
        } catch (StudentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void fileUtilities() {
        System.out.println("\n--- File Utilities ---");
        System.out.println("1. Export All Data");
        System.out.println("2. Perform Backup of Exported Data");
        System.out.println("3. Show Backup Directory Size (Recursive)");
        System.out.print("Select an option: ");
        int choice = getIntInput();
        scanner.nextLine();
        
        Path backupPath = Paths.get("data", "backups");

        switch (choice) {
            case 1:
                try {
                    dataExporter.exportAllData();
                } catch (IOException e) {
                    System.err.println("Data export failed: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    backupService.performBackup();
                } catch (IOException e) {
                    System.err.println("Backup failed: " + e.getMessage());
                }
                break;
            case 3:
                try {
                    if (!Files.exists(backupPath)) {
                        System.out.println("Backup directory doesn't exist yet.");
                        return;
                    }
                    long size = RecursiveFileUtils.calculateDirectorySize(backupPath);
                    System.out.printf("Total size of backup directory is: %d bytes (%.2f KB)\n", size, size / 1024.0);
                } catch (IOException e) {
                    System.err.println("Could not calculate directory size: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // discard non-integer input
        }
        return scanner.nextInt();
    }

    private static void loadSampleData() {
        // Sample Students
        studentService.addStudent(new Student(1, "S001", "Alice Johnson", "alice@example.com", LocalDate.of(2004, 8, 15)));
        studentService.addStudent(new Student(2, "S002", "Bob Smith", "bob@example.com", LocalDate.of(2003, 3, 22)));
        
        // Sample Courses
        courseService.addCourse(new Course.Builder("CS101", "Intro to Java", 3).department("CS").build());
        courseService.addCourse(new Course.Builder("MA201", "Calculus I", 4).department("Math").build());

        // Sample Instructor
        instructorService.addInstructor(new Instructor(901, "Dr. Stephen Strange", "strange@mcu.com", LocalDate.of(1970, 1, 1), "Mystic Arts"));
        
        System.out.println("Sample data loaded.");
    }
}