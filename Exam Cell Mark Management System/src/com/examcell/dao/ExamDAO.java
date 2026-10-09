package com.examcell.dao;

import com.examcell.connection.DBConnection;
import com.examcell.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamDAO {

    // Add Student
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students "
                + "(register_no, student_name, department, semester) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getRegisterNo());
            ps.setString(2, student.getStudentName());
            ps.setString(3, student.getDepartment());
            ps.setInt(4, student.getSemester());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding student: "
                    + e.getMessage());

            return false;
        }
    }

    // View Students
    public void viewStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== STUDENT DETAILS ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Register Number: "
                        + rs.getString("register_no"));

                System.out.println("Name: "
                        + rs.getString("student_name"));

                System.out.println("Department: "
                        + rs.getString("department"));

                System.out.println("Semester: "
                        + rs.getInt("semester"));

                System.out.println("-------------------------------------");
            }

            if (!found) {
                System.out.println("No students found.");
            }

        } catch (SQLException e) {

            System.out.println("Error viewing students: "
                    + e.getMessage());
        }
    }

    // Add Examination Marks
    public boolean addMarks(String registerNo, String subject,
                            int internal, int external) {

        if (internal < 0 || internal > 40
                || external < 0 || external > 60) {

            System.out.println("Invalid marks!");
            return false;
        }

        int total = internal + external;

        String sql = "INSERT INTO marks "
                + "(register_no, subject_name, internal_mark, "
                + "external_mark, total_mark) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, registerNo);
            ps.setString(2, subject);
            ps.setInt(3, internal);
            ps.setInt(4, external);
            ps.setInt(5, total);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding marks: "
                    + e.getMessage());

            return false;
        }
    }

    // View Student Marks
    public void viewMarks(String registerNo) {

        String sql = "SELECT s.student_name, s.register_no, "
                + "m.subject_name, m.internal_mark, "
                + "m.external_mark, m.total_mark "
                + "FROM students s "
                + "JOIN marks m ON s.register_no = m.register_no "
                + "WHERE s.register_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, registerNo);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                System.out.println("\n========== EXAM RESULTS ==========");

                while (rs.next()) {

                    found = true;

                    System.out.println("Register Number: "
                            + rs.getString("register_no"));

                    System.out.println("Student Name: "
                            + rs.getString("student_name"));

                    System.out.println("Subject: "
                            + rs.getString("subject_name"));

                    System.out.println("Internal Mark: "
                            + rs.getInt("internal_mark"));

                    System.out.println("External Mark: "
                            + rs.getInt("external_mark"));

                    System.out.println("Total Mark: "
                            + rs.getInt("total_mark") + "/100");

                    System.out.println("----------------------------------");
                }

                if (!found) {
                    System.out.println("No marks found.");
                }
            }

        } catch (SQLException e) {

            System.out.println("Error viewing marks: "
                    + e.getMessage());
        }
    }
}