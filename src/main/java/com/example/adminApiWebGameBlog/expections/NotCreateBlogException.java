package com.example.adminApiWebGameBlog.expections;

public class NotCreateBlogException extends RuntimeException{

    public NotCreateBlogException(){
        super("Not create blog");
    }
}
