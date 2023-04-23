package com.example.adminApiWebGameBlog.expections;

public class DeleteFailedException extends RuntimeException{
    public DeleteFailedException() {
    }

    public DeleteFailedException(String message) {
        super(message);
    }
}
