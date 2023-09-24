package com.example.adminservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "enrollment_v")
public class Enrollment {

    @Id
    private Integer id;
    private Integer user_id;
    private Integer class_id;
    private Integer subject_id;
    private Integer teacher_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String class_name;
    private String subject_name;
    private String teacher_name;
}
