package com.example.teacherservice.controller;

import com.example.teacherservice.service.result.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/get-lecturer")
    public ResponseEntity<Object> getLecturer() {
        return lecturerService.getLecturer();
    }
}
