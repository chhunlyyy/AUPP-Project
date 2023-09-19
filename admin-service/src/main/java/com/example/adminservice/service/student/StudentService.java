package com.example.adminservice.service.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.adminservice.entity.StudentEntity;

@Service
public interface StudentService {
    public ResponseEntity<Object> enrole(List<StudentEntity> studentEntities);
}
