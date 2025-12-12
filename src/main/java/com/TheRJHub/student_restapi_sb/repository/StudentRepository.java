package com.TheRJHub.student_restapi_sb.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TheRJHub.student_restapi_sb.model.Student;

// Integer is the type of the Primary Key (id)
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Custom Query Method: Finds all students with the given name
    List<Student> findByName(String name);

    // Custom Query Method: Finds the student with the given email (assuming email is unique)
    Student findByEmail(String email);
}