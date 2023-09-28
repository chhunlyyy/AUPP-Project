package com.example.teacherservice.service.result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.teacherservice.entity.ResultEntity;
import com.example.teacherservice.helper.ResponeHandler;
import com.example.teacherservice.repository.ResultRepository;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepo;

    @Override
    public ResponseEntity<Object> addResult(List<ResultEntity> entites) {
        resultRepo.saveAll(entites);

        return ResponeHandler.generateResponse("Add Student Result Successfully", HttpStatus.OK, null);
    }

    @Override
    public ResponseEntity<Object> getResult(int examId, int classId) {
        List<ResultEntity> results = resultRepo.getResult(examId, classId);
        if (results.isEmpty()) {
            return ResponeHandler.generateResponse("No Result Found", HttpStatus.EXPECTATION_FAILED, null);
        } else {
            return ResponeHandler.generateResponse("", HttpStatus.OK, results);
        }
    }

}
