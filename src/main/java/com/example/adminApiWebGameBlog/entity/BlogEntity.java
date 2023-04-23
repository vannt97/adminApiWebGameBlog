package com.example.adminApiWebGameBlog.entity;

import com.example.adminApiWebGameBlog.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Entity(name = "blog")
public class BlogEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private String slug;

    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",insertable = true,updatable = true)
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    private String thumbnail;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateDate(){
        this.setCreatedDate((new Date()));
        this.setModifiedDate((new Date()));
    }

/////////////////////////////////////////////////////////////////////- cach 1
//    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<BlogCategoryEntity> catogories = new HashSet<>();
//
//    public Set<BlogCategoryEntity> getCatogories() {
//        return catogories;
//    }
//
//    public void setCatogories(Set<BlogCategoryEntity> catogories) {
//        this.catogories = catogories;
//    }

//    -cach 2
    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
        })
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "blog_category",
            joinColumns = { @JoinColumn(name = "blog_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<CategoryEntity> categories = new HashSet<>();

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void addCategory(CategoryEntity category){
        this.categories.add(category);
        category.getBlogs().add(this);
    }

    public void removeCategory(long categoryId){
        CategoryEntity category = this.categories.stream().filter(t -> t.getId() == categoryId).findFirst().orElse(null);
        if(category != null){
            this.categories.remove(category);
            category.getBlogs().remove(this);
        }
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
