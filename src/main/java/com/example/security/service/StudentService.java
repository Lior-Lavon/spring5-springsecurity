package com.example.security.service;

import com.example.security.domain.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAll();

    Student getById(Long id);

    Student save(Student student);

    Student update(Student student);

    Long count();

    void deleteById(Long id);

}
