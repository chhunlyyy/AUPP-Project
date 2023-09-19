package com.example.adminservice.service.subject;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.SubjectEntity;
import com.example.adminservice.helper.ResponeHandler;
import com.example.adminservice.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepo;

    @Override
    public ResponseEntity<Object> add(SubjectEntity subjectEntity) {
        SubjectEntity subject = subjectRepo.save(subjectEntity);

        return ResponeHandler.generateResponse("Subject Added Successfully", HttpStatus.OK, subject);
    }

    @Override
    public ResponseEntity<Object> update(SubjectEntity subjectEntity) {
        // Retrieve the entity object
        Optional<SubjectEntity> optionalSubject = subjectRepo.findById(subjectEntity.getId());
        SubjectEntity subject = null;
        if (optionalSubject.isPresent()) {
            // Modify the fields of the entity object
            subject = optionalSubject.get();
            subject.setName(subjectEntity.getName());
            subject.setDescription(subjectEntity.getDescription());

            // Save the entity
            subjectRepo.save(subject);
        }

        return ResponeHandler.generateResponse("Subject Update Successfully", HttpStatus.OK, subject);
    }

    @Override
    public ResponseEntity<Object> delete(int id) {
        Optional<SubjectEntity> optionalSubject = subjectRepo.findById(id);
        if (optionalSubject.isPresent()) {
            subjectRepo.flush();
            subjectRepo.delete(optionalSubject.get());
            return ResponeHandler.generateResponse("Subject Delete Successfully", HttpStatus.OK, null);
        } else {
            return ResponeHandler.generateResponse("Subject Not Exist", HttpStatus.EXPECTATION_FAILED, null);
        }

    }

    @Override
    public ResponseEntity<Object> search(String name) {
        List<SubjectEntity> result = subjectRepo.findByName(name);
        return ResponeHandler.generateResponse(result.size() > 0 ? "" : "No Subject found", HttpStatus.OK, result);
    }

    @Override
    public ResponseEntity<Object> list() {
        return ResponeHandler.generateResponse("", HttpStatus.OK, subjectRepo.findAll());
    }

}
