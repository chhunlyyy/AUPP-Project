package com.example.teacherservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.teacherservice.entity.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Integer>{
    @Query(value = "DELETE FROM user WHERE id = ?id", nativeQuery = true)
    void deleteLecturer(Integer id);
}