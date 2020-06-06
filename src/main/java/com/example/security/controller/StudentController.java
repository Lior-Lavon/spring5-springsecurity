package com.example.security.controller;

import com.example.security.domain.Student;
import com.example.security.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getAll(){
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student getById(@PathVariable Long id){
        return studentService.getById(id);
    }

}
