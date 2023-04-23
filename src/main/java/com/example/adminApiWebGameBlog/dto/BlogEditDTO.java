package com.example.adminApiWebGameBlog.dto;

import com.example.adminApiWebGameBlog.enums.EStatus;

import java.util.List;

public class BlogEditDTO {
    private String slugFirst;
    private String title;
    private String content;
    private EStatus status;
    private List<Integer> categories;
    private String thumbnail;
    private String accountName;
    private String slug;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlugFirst() {
        return slugFirst;
    }

    public void setSlugFirst(String slugFirst) {
        this.slugFirst = slugFirst;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "BlogDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", role=" + categories +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
