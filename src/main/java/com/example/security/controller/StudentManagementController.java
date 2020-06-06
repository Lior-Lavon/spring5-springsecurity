package com.example.security.controller;

import com.example.security.domain.Student;
import com.example.security.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private final StudentService studentService;

    public StudentManagementController(StudentService studentService) {
        this.studentService = studentService;
    }

//    hasRole('ROLE_')
//    hasAnyRole('ROLE_')
//    hasAuthority('permission')
//    hasAnyAuthority('permission')

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getAllStudent(){
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudentById(@PathVariable Long id){
        return studentService.getById(id);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('student:write')")
    @ResponseStatus(HttpStatus.CREATED)
    public Student registerNewStudent (@RequestBody Student student){
        return studentService.save(student);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    @ResponseStatus(HttpStatus.OK)
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student){
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteById(id);
    }




}
