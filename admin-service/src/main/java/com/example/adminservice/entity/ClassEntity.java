package com.example.adminservice.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "class")
@Data
@NoArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int teacher_id;
    private int subject_id;

    @OneToOne()
    @JoinColumn(name = "teacher_id",referencedColumnName = "id",insertable=false, updatable=false)
    LecturerEntity teacher;

    @OneToOne()
    @JoinColumn(name = "subject_id", referencedColumnName = "id", insertable=false, updatable=false)
    SubjectEntity subject;

    @OneToMany(mappedBy = "classEntity")
    List<StudentEntity> students;

}
