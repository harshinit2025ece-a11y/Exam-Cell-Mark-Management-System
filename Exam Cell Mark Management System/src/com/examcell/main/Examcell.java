package com.examcell.main;

import com.examcell.connection.DBConnection;
import com.examcell.model.Student;
import com.examcell.dao.ExamDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Examcell {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExamDAO dao = new ExamDAO();

        // Test database connection
        try (Connection con = DBConnection.getConnection()) {

            System.out.println("======================================");
            System.out.println("   EXAM CELL MARK MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("Database Connected Successfully!");

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");
            System.out.println(e.getMessage());

            sc.close();
            return;
        }

        int choice = 0;

        do {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Enter Examination Marks");
            System.out.println("4. View Student Marks");
            System.out.println("5. Exit");
            System.out.println("===============================");

            System.out.print("Enter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {

                    case 1:

                        System.out.print("Register Number: ");
                        String regNo = sc.nextLine().trim();

                        System.out.print("Student Name: ");
                        String name = sc.nextLine().trim();

                        System.out.print("Department: ");
                        String dept = sc.nextLine().trim();

                        System.out.print("Semester: ");
                        int semester = Integer.parseInt(
                                sc.nextLine().trim()
                        );

                        if (regNo.isEmpty() || name.isEmpty()
                                || dept.isEmpty()
                                || semester < 1 || semester > 8) {

                            System.out.println(
                                    "Please enter valid student details."
                            );

                            break;
                        }

                        Student student = new Student(
                                regNo, name, dept, semester
                        );

                        if (dao.addStudent(student)) {
                            System.out.println(
                                    "Student added successfully!"
                            );
                        }

                        break;

                    case 2:

                        dao.viewStudents();
                        break;

                    case 3:

                        System.out.print("Register Number: ");
                        String markRegNo = sc.nextLine().trim();

                        System.out.print("Subject Name: ");
                        String subject = sc.nextLine().trim();

                        System.out.print("Internal Mark (0-40): ");
                        int internal = Integer.parseInt(
                                sc.nextLine().trim()
                        );

                        System.out.print("External Mark (0-60): ");
                        int external = Integer.parseInt(
                                sc.nextLine().trim()
                        );

                        if (markRegNo.isEmpty() || subject.isEmpty()) {
                            System.out.println(
                                    "Register number and subject are required."
                            );
                            break;
                        }

                        if (dao.addMarks(
                                markRegNo, subject, internal, external)) {

                            System.out.println("Marks added successfully!");
                            System.out.println(
                                    "Total: " + (internal + external) + "/100"
                            );
                        }

                        break;

                    case 4:

                        System.out.print("Enter Register Number: ");
                        String searchRegNo = sc.nextLine().trim();

                        dao.viewMarks(searchRegNo);
                        break;

                    case 5:

                        System.out.println("Exiting the system.");
                        break;

                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }

        } while (choice != 5);

        sc.close();
    }
}