package com.example.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.adminservice.entity.StudentRo;

@Repository
public interface StudentRoRepository extends JpaRepository<StudentRo,Integer> {
    
}
