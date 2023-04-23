package com.example.adminApiWebGameBlog.repository;

import com.example.adminApiWebGameBlog.enums.ERole;
import com.example.adminApiWebGameBlog.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    Optional<RoleEntity> findByName(ERole name);
}
