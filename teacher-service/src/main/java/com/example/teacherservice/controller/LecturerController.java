package com.example.teacherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teacherservice.service.result.LecturerService;

@RestController
@RequestMapping("/teacher")
public class LecturerController {
    @Autowired
    private LecturerService service;

    @GetMapping("/get")
    public ResponseEntity<Object> get() {
        return service.getLecturer();
    }
}
