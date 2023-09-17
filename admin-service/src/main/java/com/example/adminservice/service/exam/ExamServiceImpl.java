package com.example.adminservice.service.exam;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.ClassEntity;
import com.example.adminservice.entity.ExamEntity;
import com.example.adminservice.helper.ResponeHandler;
import com.example.adminservice.repository.ExamRepository;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository repo;

    @Override
    public ResponseEntity<Object> list() {
        return ResponeHandler.generateResponse("", HttpStatus.OK, repo.findAll());
    }

    @Override
    public ResponseEntity<Object> add(ExamEntity examEntity) {
        ExamEntity exam = repo.save(examEntity);

        return ResponeHandler.generateResponse("Exam Added Successfully", HttpStatus.OK, exam);
    }

    @Override
    public ResponseEntity<Object> update(ExamEntity examEntity) {
        // Retrieve the entity object
        Optional<ExamEntity> optionalExam = repo.findById(examEntity.getId());
        ExamEntity exam = null;
        if (optionalExam.isPresent()) {
            // Modify the fields of the entity object
            exam = optionalExam.get();
            exam.setName(examEntity.getName());
            exam.setDescription(examEntity.getDescription());
            exam.setDate(examEntity.getDate());
            exam.setMax_score(examEntity.getMax_score());
            exam.setClass_id(examEntity.getClass_id());

            // Save the entity
            repo.save(exam);
        }

        return ResponeHandler.generateResponse("Exam Update Successfully", HttpStatus.OK, examEntity);
    }

    @Override
    public ResponseEntity<Object> delete(int id) {
        Optional<ExamEntity> optionalSubject = repo.findById(id);
        if (optionalSubject.isPresent()) {
            repo.flush();
            repo.delete(optionalSubject.get());
            return ResponeHandler.generateResponse("Exam Delete Successfully", HttpStatus.OK, null);
        } else {
            return ResponeHandler.generateResponse("Exam Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        }
    }

    @Override
    public ResponseEntity<Object> examByClass(int classId) {
        List<ExamEntity> results = repo.examByClass(classId);
        if (results.isEmpty()) {
            return ResponeHandler.generateResponse("Exam Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        } else {
            return ResponeHandler.generateResponse("", HttpStatus.OK, results);
        }
    }

}
