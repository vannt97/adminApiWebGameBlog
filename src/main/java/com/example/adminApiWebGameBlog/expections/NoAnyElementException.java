package com.example.adminApiWebGameBlog.expections;

public class NoAnyElementException extends RuntimeException{

    public NoAnyElementException() {
        super("Không tìm thấy element trong database");
    }
}
