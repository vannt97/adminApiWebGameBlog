package com.example.adminApiWebGameBlog.entity;

import com.example.adminApiWebGameBlog.enums.EStatusCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "category")
public class CategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Enumerated(EnumType.STRING)
    private EStatusCategory status;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "categories")
    @JsonIgnore
    private Set<BlogEntity> blogs = new HashSet<>();

    public Set<BlogEntity> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<BlogEntity> blogs) {
        this.blogs = blogs;
    }
    //    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<BlogCategoryEntity> blogs = new HashSet<>();
//
//    public Set<BlogCategoryEntity> getBlogs() {
//        return blogs;
//    }
//
//    public void setBlogs(Set<BlogCategoryEntity> blogs) {
//        this.blogs = blogs;
//    }

    public EStatusCategory getStatus() {
        return status;
    }

    public void setStatus(EStatusCategory status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
