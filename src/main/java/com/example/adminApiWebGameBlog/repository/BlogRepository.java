package com.example.adminApiWebGameBlog.repository;

import com.example.adminApiWebGameBlog.entity.BlogEntity;
import com.example.adminApiWebGameBlog.entity.CategoryEntity;
import com.example.adminApiWebGameBlog.enums.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Long>, CrudRepository<BlogEntity,Long> {
    public List<BlogEntity> findAll();

    public Page<BlogEntity> findByTitleContains(String title, Pageable pageable);

    public List<BlogEntity> findAllByOrderByIdDesc();

    public List<BlogEntity> findBySlug(String slug);

    public Optional<BlogEntity> findById(long id);

    public void deleteById(long id);

    @Query("SELECT count(id) FROM blog ")
    long getSizeBlog();

    public List<BlogEntity> findTop12ByStatusOrderByCreatedDateDesc(EStatus status);

    public Page<BlogEntity> findByStatus(EStatus status, Pageable pageable);

    public  Page<BlogEntity> findByStatusAndTitleContains(EStatus status, String title, Pageable pageable);

    public Page<BlogEntity> findByCategories(CategoryEntity category, Pageable pageable);
}
