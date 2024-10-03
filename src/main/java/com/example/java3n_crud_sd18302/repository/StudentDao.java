package com.example.java3n_crud_sd18302.repository;

import com.example.java3n_crud_sd18302.entity.Student;
import com.example.java3n_crud_sd18302.jdbc.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDao {

    private static DatabaseConnectionManager dcm =
            new DatabaseConnectionManager("test1", "sa", "123456");


    // fake data
    private static ArrayList<Student> students = new ArrayList<>();
    static {
        students.add(new Student(1001L, "student 1", "email 1", "phone 1"));
        students.add(new Student(1002L, "student 2", "email 2", "phone 2"));
        students.add(new Student(1003L, "student 3", "email 3", "phone 3"));
    }

    public ArrayList<Student> getStudents() {

        ArrayList<Student> students1 = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = dcm.getConnection();

            String sql = """
                        SELECT * FROM students;
                    """;
            preparedStatement = connection.prepareStatement(sql);
            //preparedStatement.setLong(1, 1001L);
            //preparedStatement.setString(2, "student 1");
            //preparedStatement.setString(3, "email 1");
            //preparedStatement.setString(4, "phone 1");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhone(resultSet.getString("phone"));

                students1.add(student);
            }

            System.out.println("done...");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dcm.close(resultSet, preparedStatement, connection);
        }


        return students1;
    }

    public void deleteStudent(Long id) {

        //students.removeIf(student -> student.getId().equals(id));

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = dcm.getConnection();

            String sql = """
                        DELETE FROM students WHERE id=?;
                    """;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            System.out.println("done...");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dcm.close(null, preparedStatement, connection);
        }
    }

    public void addStudent(Student student) {

        //students.add(student);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = dcm.getConnection();

            String sql = """
                        INSERT INTO students(id, name, email, phone)
                        VALUES(?, ?, ?, ?)
                    """;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getPhone());

            preparedStatement.executeUpdate();

            System.out.println("done...");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dcm.close(null, preparedStatement, connection);
        }

    }

    public Student getStudentById(Long id) {

        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateStudent(Student student) {

        // students.set(index, student)
        students.set(students.indexOf(getStudentById(student.getId())), student);
    }
}
