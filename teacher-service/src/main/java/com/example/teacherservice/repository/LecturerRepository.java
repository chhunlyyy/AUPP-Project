package com.example.teacherservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.teacherservice.entity.Lecturer;
import com.example.teacherservice.entity.ResultEntity;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Integer>{
    
}
