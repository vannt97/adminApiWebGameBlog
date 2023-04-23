package com.example.adminApiWebGameBlog.expections;

public class SaveFailedException extends RuntimeException{

    public SaveFailedException() {
    }

    public SaveFailedException(String message) {
        super(message);
    }
}
