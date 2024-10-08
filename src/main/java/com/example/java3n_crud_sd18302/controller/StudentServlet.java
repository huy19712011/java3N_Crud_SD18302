package com.example.java3n_crud_sd18302.controller;

import com.example.java3n_crud_sd18302.entity.Student;
import com.example.java3n_crud_sd18302.service.StudentService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

//@WebServlet(name = "StudentServlet", value = "/StudentServlet/*")
@WebServlet(name = "StudentServlet", value = {
        "/students",
        "/students/new",
        "/students/insert",
        "/students/edit",
        "/students/update",
        "/students/delete"
})
public class StudentServlet extends HttpServlet {

    private StudentService service = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/*        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("getServletPath(): " + request.getServletPath());
        out.println("<br>");
        out.println("getRequestURL(): " + request.getRequestURL());
        out.println("<br>");
        out.println("getQueryString(): " + request.getQueryString());
        out.println("<br>");

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            out.println(entry.getKey() + " : " + entry.getValue()[0]);
        }

        out.println("</body></html");*/

        String path = request.getServletPath();
        System.out.println(path);

        switch (path) {
            case "/students":
                listStudents(request, response);
                break;
            case "/students/new":
                showNewForm(request, response);
                break;
            case "/students/insert":
                insertStudent(request, response);
                break;
            case "/students/edit":
                editStudent(request, response);
                break;
            case "/students/update":
                updateStudent(request, response);
                break;
            case "/students/delete":
                deleteStudent(request, response);
                break;
        }

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // read info from form
        Student student = getStudentFromForm(request);

        // update list of students
        service.updateStudent(student);

        // back to table
        response.sendRedirect("/students");

    }

    private Student getStudentFromForm(HttpServletRequest request) {
        // read student info from form
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // new student
        return new Student(id, name, email, phone);
    }

    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // load student to form
        // get student id
        Long id = Long.parseLong(request.getParameter("id"));

        // get student by id
        Student student = service.getStudentById(id);

        // send info to form
        request.setAttribute("student", student);
        request.getRequestDispatcher("/view/updateStudentForm.jsp")
                .forward(request, response);

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/view/addStudentForm.jsp")
                .forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // get Id
        Long id = Long.parseLong(request.getParameter("id"));

        // delete
        service.deleteStudent(id);

        // show table
        response.sendRedirect("/students");
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // read student info from form
        Student student = getStudentFromForm(request);

        // add to list
        service.addStudent(student);

        // back to table
        response.sendRedirect("/students");
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //data: students
        ArrayList<Student> students = new ArrayList<>();
        students = service.getStudents();

        //students.add(new Student(1001L, "student 1", "email 1", "phone 1"));
        //students.add(new Student(1002L, "student 2", "email 2", "phone 2"));
        //students.add(new Student(1003L, "student 3", "email 3", "phone 3"));

        // => jsp
        request.setAttribute("students", students);
        request.getRequestDispatcher("/view/studentList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
