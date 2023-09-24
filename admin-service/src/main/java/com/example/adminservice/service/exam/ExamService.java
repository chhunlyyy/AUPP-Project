package com.example.adminservice.service.exam;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.ExamEntity;

@Service
public interface ExamService {
    /**
     * 
     * @return
     */
    public ResponseEntity<Object> list();

      /**
     * 
     * @return
     */
    public ResponseEntity<Object> examByClass(int classId);

     /**
     * 
     * @return
     */
    public ExamEntity examById(int examId);

    /**
     * 
     * @param examEntity
     * @return
     */
    public ResponseEntity<Object> add(ExamEntity examEntity);

    /**
     * 
     * @param examEntity
     * @return
     */
    public ResponseEntity<Object> update(ExamEntity examEntity);

    /**
     * 
     * @param id
     * @return
     */
    public ResponseEntity<Object> delete(int id);
}
