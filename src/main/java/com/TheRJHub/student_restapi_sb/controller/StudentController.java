package com.TheRJHub.student_restapi_sb.controller;
import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.TheRJHub.student_restapi_sb.model.Student;
import com.TheRJHub.student_restapi_sb.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/add")
    public String addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return "Student saved successfully: " + student.getName();
    }
    
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found for id: " + id));
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        Student existingStudent = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found for id: " + id));

        // Update fields
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        
        return studentRepository.save(existingStudent);
    }
    
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found for id: " + id);
        }
        
        studentRepository.deleteById(id);
        return "Student with id " + id + " deleted successfully.";
    }

    @GetMapping("/search")
    public List<Student> getStudentsByParam(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        
        if (name != null) {
            return studentRepository.findByName(name);
        } else if (email != null) {
            Student student = studentRepository.findByEmail(email);
            return (student != null) ? List.of(student) : Collections.emptyList();
        } else {
            return Collections.emptyList(); 
        }
    }
}
