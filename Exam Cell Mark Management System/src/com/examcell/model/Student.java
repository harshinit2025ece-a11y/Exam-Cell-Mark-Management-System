package com.examcell.model;

public class Student {

    private String registerNo;
    private String studentName;
    private String department;
    private int semester;

    public Student(String registerNo, String studentName,
                   String department, int semester) {

        this.registerNo = registerNo;
        this.studentName = studentName;
        this.department = department;
        this.semester = semester;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDepartment() {
        return department;
    }

    public int getSemester() {
        return semester;
    }
}