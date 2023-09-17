package com.example.adminservice.controller;

import java.util.List;

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

import com.example.adminservice.entity.ClassEntity;
import com.example.adminservice.entity.StudentEntity;
import com.example.adminservice.service.class_.ClassService;
import com.example.adminservice.service.student.StudentService;

@RestController
@RequestMapping("/admin/class")
public class ClassController {
    @Autowired
    private ClassService service;

    @Autowired
    private StudentService studentService;

    @GetMapping("/detail")
    public ResponseEntity<Object> add(@RequestParam int id) {
        return service.detail(id);
    }
     @GetMapping("/by-teacher")
    public ResponseEntity<Object> enrollStudent(@RequestParam int teacherId) {
        return service.classByTeacher(teacherId);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody ClassEntity classEntity) {
        return service.add(classEntity);
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ClassEntity classEntity) {
        return service.update(classEntity);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id) {
        return service.delete(id);
    }

    @PostMapping("/enroll-student")
    public ResponseEntity<Object> enrollStudent(@RequestBody List<StudentEntity> studentEntities) {
        return studentService.enrole(studentEntities);
    }
   
}
