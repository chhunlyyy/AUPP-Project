package com.example.adminservice.service.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.example.adminservice.entity.StudentEntity;

public interface StudentService {
    public ResponseEntity<Object> enrole(List<StudentEntity> studentEntities);
    public ResponseEntity<Object> updateEnrollment(StudentEntity enrollment);
    public ResponseEntity<Object> getStudent();
}
