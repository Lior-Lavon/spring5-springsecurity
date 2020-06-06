package com.example.security.bootstrap;

import com.example.security.domain.Student;
import com.example.security.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoadData implements CommandLineRunner {

    private final StudentService studentService;

    public LoadData(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void run(String... args) throws Exception {

        LoadStudents();
    }

    private void LoadStudents(){

        Student s1 = Student.builder().firstName("Lior").lastName("Lavon").build();
        Student s2 = Student.builder().firstName("Meitar").lastName("Lavon").build();
        Student s3 = Student.builder().firstName("Noa").lastName("Lavon").build();

        studentService.save(s1);
        studentService.save(s2);
        studentService.save(s3);

        log.debug("LoadStudents : " + studentService.count());
    }
}
