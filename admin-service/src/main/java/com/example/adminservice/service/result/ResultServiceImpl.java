package com.example.adminservice.service.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.ResultEntity;
import com.example.adminservice.repository.ResultRepository;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepo;

    @Override
    public List<ResultEntity> getResult(int examId, int classId) {
        List<ResultEntity> results = resultRepo.getResult(examId, classId);
        if (results.isEmpty()) {
            return new ArrayList<ResultEntity>();
        } else {
            return results;
        }
    }

}
