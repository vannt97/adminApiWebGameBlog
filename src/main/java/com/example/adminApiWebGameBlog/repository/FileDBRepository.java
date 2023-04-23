package com.example.adminApiWebGameBlog.repository;

import com.example.adminApiWebGameBlog.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDBRepository extends JpaRepository<FileEntity, String> {

    public boolean existsByName(String name);
}
