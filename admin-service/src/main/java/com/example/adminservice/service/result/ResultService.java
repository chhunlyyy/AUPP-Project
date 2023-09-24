package com.example.adminservice.service.result;


import java.util.List;
import org.springframework.stereotype.Service;

import com.example.adminservice.entity.ResultEntity;

@Service
public interface ResultService {
      public List<ResultEntity> getResult(int examId,int classId);

}
