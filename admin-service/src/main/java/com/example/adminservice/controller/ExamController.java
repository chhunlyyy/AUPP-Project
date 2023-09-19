package com.example.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminservice.entity.ExamEntity;
import com.example.adminservice.service.exam.ExamService;

@RestController
@RequestMapping("/admin/exam")
public class ExamController {
    @Autowired
    private ExamService service;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return service.list();
    }

    @GetMapping("/by-class")
    public ResponseEntity<Object> byClass(@RequestParam int classId) {
        return service.examByClass(classId);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody ExamEntity examEntity) {
        return service.add(examEntity);
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ExamEntity examEntity) {
        return service.update(examEntity);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id) {
        return service.delete(id);
    }

}
