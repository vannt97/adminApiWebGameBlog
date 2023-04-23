package com.example.adminApiWebGameBlog.entity.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class BlogCategoryId implements Serializable {

    @Column(name = "blog_id", nullable = false)
    private long blogId;

    @Column(name = "category_id", nullable = false, insertable = true, updatable = true)
    private long categoryId;

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
