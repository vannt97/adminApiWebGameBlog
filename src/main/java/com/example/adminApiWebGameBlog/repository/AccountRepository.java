package com.example.adminApiWebGameBlog.repository;

import com.example.adminApiWebGameBlog.entity.AccountEntity;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long>, CrudRepository<AccountEntity,Long> {
    public List<AccountEntity> findAll();

    public List<AccountEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT count(id) FROM account ")
    long getSizeAccount();

}
