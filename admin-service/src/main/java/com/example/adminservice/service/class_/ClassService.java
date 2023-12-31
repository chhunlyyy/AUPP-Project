package com.example.adminservice.service.class_;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.ClassEntity;

@Service
public interface ClassService {

    /**
     * 
     * @return
     */
    public ResponseEntity<Object> detail(int id);

    /**
     * 
     * @param classEntity
     * @return
     */

    public ResponseEntity<Object> add(ClassEntity classEntity);

    /**
     * 
     * @param classEntity
     * @return
     */
    public ResponseEntity<Object> update(ClassEntity classEntity);

     /**
     * 
     * @param classEntity
     * @return
     */
    public ResponseEntity<Object> classByTeacher(int teacherId);

    /**
     * 
     * @param id
     * @return
     */
    public ResponseEntity<Object> delete(int id);
    
    public ResponseEntity<Object> removeStudent(int id);

    ResponseEntity<Object> list();
    
    ResponseEntity<Object> getEnrollment();

}
