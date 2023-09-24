package com.example.teacherservice.controller;

import java.util.List;

import com.example.teacherservice.service.result.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.teacherservice.entity.ResultEntity;
import com.example.teacherservice.service.result.ResultService;

@RestController
@RequestMapping("teacher/result")
public class ResultController {

    @Autowired
    private ResultService service;

    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/get-lecturer")
    public ResponseEntity<Object> getLecturer() {
        return lecturerService.getLecturer();
    }
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody List<ResultEntity> entites) {
        return service.addResult(entites);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam int examId, @RequestParam int classId) {
        return service.getResult(examId, classId);
    }

}
