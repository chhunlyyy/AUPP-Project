package com.example.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.adminservice.entity.ExamEntity;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity,Integer>{
    @Query(value = "SELECT * FROM exam WHERE class_id =?1",nativeQuery = true)
    List<ExamEntity> examByClass(int classId);
}
