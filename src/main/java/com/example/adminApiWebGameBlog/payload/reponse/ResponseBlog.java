package com.example.adminApiWebGameBlog.payload.reponse;

import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.entity.CategoryEntity;
import com.example.adminApiWebGameBlog.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class ResponseBlog {

    private long id;

    private String content;

    private String slug;

    private String title;

    private AccountEntity account;

    private EStatus status;

    private String thumbnail;

    private String description;

    private String createdDate;

    private String modifiedDate;

    private Set<CategoryEntity> categories;

    public ResponseBlog() {
    }

    public ResponseBlog(long id, String content, String slug, String title, AccountEntity account, EStatus status, String thumbnail, String description, String createdDate, String modifiedDate, Set<CategoryEntity> categories) {
        this.id = id;
        this.content = content;
        this.slug = slug;
        this.title = title;
        this.account = account;
        this.status = status;
        this.thumbnail = thumbnail;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.categories = categories;
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

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }
}
