package com.example.teacherservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.teacherservice.entity.ResultEntity;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity,Integer>{
    
    @Query(value = "SELECT * FROM result WHERE exam_id = ?1 AND class_id = ?2",nativeQuery = true)
    List<ResultEntity> getResult (int examId,int classId);
}
