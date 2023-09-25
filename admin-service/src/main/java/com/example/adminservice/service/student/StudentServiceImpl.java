package com.example.adminservice.service.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.StudentEntity;
import com.example.adminservice.helper.ResponeHandler;
import com.example.adminservice.repository.StudentRepository;
import com.example.adminservice.repository.StudentRoRepository;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository repo;
    
    @Autowired
    private StudentRoRepository studentRoRepository;

    @Override
    public ResponseEntity<Object> enrole(List<StudentEntity> studentEntities) {

        repo.saveAll(studentEntities);

        return ResponeHandler.generateResponse("Enroll Student Successfully", HttpStatus.OK, null);
    }
    
    @Override
    public ResponseEntity<Object> getStudent() {
        return ResponeHandler.generateResponse(null, HttpStatus.OK, studentRoRepository.findAll());
    }
}
