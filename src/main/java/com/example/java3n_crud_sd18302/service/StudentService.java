package com.example.java3n_crud_sd18302.service;

import com.example.java3n_crud_sd18302.entity.Student;
import com.example.java3n_crud_sd18302.repository.StudentDao;

import java.util.ArrayList;

public class StudentService {

    private StudentDao studentDao = new StudentDao();

    public ArrayList<Student> getStudents() {

        return studentDao.getStudents();
    }

    public void deleteStudent(Long id) {

        studentDao.deleteStudent(id);
    }

    public void addStudent(Student student) {

        studentDao.addStudent(student);
    }
}
