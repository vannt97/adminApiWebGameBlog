package com.example.adminApiWebGameBlog.repository;

import com.example.adminApiWebGameBlog.entity.BlogEntity;
import com.example.adminApiWebGameBlog.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>, CrudRepository<CategoryEntity,Long> {

    public List<CategoryEntity> findAll();

    @Query("SELECT count(id) FROM category ")
    long getSizeCategory();

    public List<CategoryEntity> findById(long id);

    public Optional<CategoryEntity> findBySlug(String slug);
}
