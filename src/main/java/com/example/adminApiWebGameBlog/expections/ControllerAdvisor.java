package com.example.adminApiWebGameBlog.expections;

import com.example.adminApiWebGameBlog.message.ResponseMessage;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.FileAlreadyExistsException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
    }

    @ExceptionHandler(FileCanntUploadException.class)
    public ResponseEntity<ResponseData> handleFileAlreadyExistsException(RuntimeException ex, WebRequest request) {
        ResponseData responseData = new ResponseData(HttpStatus.EXPECTATION_FAILED.value(),ex.getMessage(),false);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseData);
    }

    @ExceptionHandler(SaveFailedException.class)
    public ResponseEntity<ResponseData> handleSaveFailedException(RuntimeException ex, WebRequest request){
        ResponseData responseData = new ResponseData(HttpStatus.EXPECTATION_FAILED.value(),ex.getMessage(),false);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseData);
    }

    @ExceptionHandler(DeleteFailedException.class)
    public ResponseEntity<ResponseData> handleDeleteFailedException(RuntimeException ex, WebRequest request){
        ResponseData responseData = new ResponseData(HttpStatus.EXPECTATION_FAILED.value(),ex.getMessage(),false);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseData);
    }

    @ExceptionHandler(NoAnyElementException.class)
    public ResponseEntity<ResponseData> handleNoAnyElementException(RuntimeException ex, WebRequest request){
        ResponseData responseData = new ResponseData(HttpStatus.EXPECTATION_FAILED.value(),ex.getMessage(),false);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseData);
    }
}
