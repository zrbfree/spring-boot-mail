package com.rick.demo.service;

import com.rick.demo.entity.Demo;
import com.rick.demo.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    public List<Demo> likeName(String name){
        return demoMapper.likeName(name);
    }
}
