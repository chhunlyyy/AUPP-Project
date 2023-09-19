package com.example.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.adminservice.entity.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity,Integer>{
    @Query(value = "SELECT * FROM class WHERE teacher_id =?1",nativeQuery = true)
    List<ClassEntity> classByTeacher(int teacherId);
}
