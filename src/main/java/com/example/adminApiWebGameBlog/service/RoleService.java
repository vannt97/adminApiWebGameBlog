package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.entity.RoleEntity;
import com.example.adminApiWebGameBlog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<RoleEntity> getList(){
        return roleRepository.findAll();
    }

}
