package com.example.teacherservice.service.result;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.teacherservice.entity.ResultEntity;

@Service
public interface ResultService {
     public ResponseEntity<Object> addResult(List<ResultEntity> entites);
      public ResponseEntity<Object> getResult(int examId,int classId);

}
