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

import com.example.adminservice.entity.SubjectEntity;
import com.example.adminservice.service.subject.SubjectService;

@RestController
@RequestMapping("/admin/subject")
public class SubjectController {
    @Autowired
    private SubjectService service;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return service.list();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody SubjectEntity subjectEntity) {
        return service.add(subjectEntity);
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> update(@RequestBody SubjectEntity subjectEntity) {
        return service.update(subjectEntity);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id) {
        return service.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return service.search(name);
    }
}
