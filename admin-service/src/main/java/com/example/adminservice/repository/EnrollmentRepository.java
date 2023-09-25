package com.example.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adminservice.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer> {
    
}
