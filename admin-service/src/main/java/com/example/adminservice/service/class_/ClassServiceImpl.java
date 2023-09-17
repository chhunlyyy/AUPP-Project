package com.example.adminservice.service.class_;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.ClassEntity;
import com.example.adminservice.helper.ResponeHandler;
import com.example.adminservice.repository.ClassRepository;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository repo;

    @Override
    public ResponseEntity<Object> add(ClassEntity classEntity) {
        ClassEntity class_ = repo.save(classEntity);

        return ResponeHandler.generateResponse("Class Added Successfully", HttpStatus.OK, class_);
    }

    @Override
    public ResponseEntity<Object> update(ClassEntity classEntity) {
        // Retrieve the entity object
        Optional<ClassEntity> optionalClass = repo.findById(classEntity.getId());
        ClassEntity class_ = null;
        if (optionalClass.isPresent()) {
            // Modify the fields of the entity object
            class_ = optionalClass.get();
            class_.setName(classEntity.getName());
            class_.setDescription(classEntity.getDescription());
            class_.setSubject_id(classEntity.getSubject_id());
            class_.setTeacher_id(classEntity.getTeacher_id());
            // Save the entity
            repo.save(class_);
        }

        return ResponeHandler.generateResponse("Class Update Successfully", HttpStatus.OK, classEntity);
    }

    @Override
    public ResponseEntity<Object> delete(int id) {
        Optional<ClassEntity> optionalClass = repo.findById(id);
        if (optionalClass.isPresent()) {
            repo.flush();
            repo.delete(optionalClass.get());
            return ResponeHandler.generateResponse("Class Delete Successfully", HttpStatus.OK, null);
        } else {
            return ResponeHandler.generateResponse("Class Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        }
    }

    @Override
    public ResponseEntity<Object> detail(int id) {
        Optional<ClassEntity> optionalClass = repo.findById(id);
        if (optionalClass.isPresent()) {
            return ResponeHandler.generateResponse("", HttpStatus.OK, optionalClass.get());
        } else {
            return ResponeHandler.generateResponse("Class Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        }
    }

    @Override
    public ResponseEntity<Object> classByTeacher(int teacherId) {
        List<ClassEntity> results = repo.classByTeacher(teacherId);
        if (results.isEmpty()) {
            return ResponeHandler.generateResponse("Class Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        } else {
            return ResponeHandler.generateResponse("", HttpStatus.OK, results);
        }
    }

}
