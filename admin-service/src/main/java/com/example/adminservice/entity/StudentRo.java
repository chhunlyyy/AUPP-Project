package com.example.adminservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student_v")
public class StudentRo {
    
    @Id
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
