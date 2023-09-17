package com.example.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adminservice.entity.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity,Integer>{
    
}
