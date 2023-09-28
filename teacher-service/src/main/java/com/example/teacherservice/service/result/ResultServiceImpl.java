package com.example.teacherservice.service.result;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        
        List<ResultEntity> finalResult = new ArrayList<>();

       for(ResultEntity resultEntity : entites){
        int score = resultEntity.getScore();
        String grade = "";
            if (score >= 90) {
                grade = "A";
            } else if (score >= 80) {
                grade = "B";
            } else if (score >= 70) {
                grade = "C";
            } else if (score >= 60) {
                grade = "D";
            } else if (score >= 50) {
                grade = "E";
            } else {
                grade = "F";
            }

            resultEntity.setGrade(grade);

            finalResult.add(resultEntity);
       }
        
    // entites.forEach((item) -> {
    //     if (item.getScore() >= 90) {
    //             grade = "A";
    //         } else if (score >= 80) {
    //             grade = "B";
    //         } else if (score >= 70) {
    //             grade = "C";
    //         } else if (score >= 60) {
    //             grade = "D";
    //         } else if (score >= 50) {
    //             grade = "E";
    //         } else {
    //             grade = "F";
    //         }
    // });


        for (int i = 0; i < finalResult.size(); i++) {
            try {
                resultRepo.save(finalResult.get(i));
            } catch (Exception e) { }
        }


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
