package com.example.adminApiWebGameBlog.expections;

public class FileCanntUploadException extends RuntimeException{

    public FileCanntUploadException() {
    }
    public FileCanntUploadException(String message) {
        super(message);
    }
}
