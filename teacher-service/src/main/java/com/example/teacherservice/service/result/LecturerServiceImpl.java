package com.example.teacherservice.service.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.teacherservice.helper.ResponeHandler;
import com.example.teacherservice.repository.LecturerRepository;

@Service
public class LecturerServiceImpl implements LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public ResponseEntity<Object> getLecturer() {
        return ResponeHandler.generateResponse("", HttpStatus.OK, lecturerRepository.findAll());
    }
}
