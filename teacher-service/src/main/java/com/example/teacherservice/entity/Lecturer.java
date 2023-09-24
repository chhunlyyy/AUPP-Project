package com.example.teacherservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "lecturer_v")
public class Lecturer {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
