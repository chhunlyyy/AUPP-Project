package com.example.adminservice.service.subject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.SubjectEntity;

@Service
public interface SubjectService {

    /**
     * 
     * @return
     */
    public ResponseEntity<Object> list();

    /**
     * 
     * @param subjectEntity
     * @return
     */
    public ResponseEntity<Object> add(SubjectEntity subjectEntity);

    /**
     * 
     * @param subjectEntity
     * @return
     */

    public ResponseEntity<Object> update(SubjectEntity subjectEntity);

    /**
     * 
     * @param id
     * @return
     */

    public ResponseEntity<Object> delete(int id);

    /**
     * 
     * @param name
     * @return
     */

    public ResponseEntity<Object> search(String name);
}
