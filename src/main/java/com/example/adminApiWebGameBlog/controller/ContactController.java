package com.example.adminApiWebGameBlog.controller;

import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import com.example.adminApiWebGameBlog.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ContactController {


    @Autowired
    ContactService contactService;

    @PostMapping("/contact-us")
    public ResponseEntity<?> contactUs(@RequestParam String name, @RequestParam String subject, @RequestParam String email, @RequestParam String message){
        ResponseData responseData = new ResponseData();
        if(contactService.saveContact(name,email,subject,message)){
            responseData.setSucces(true);
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData("Send success");
        }else {
            responseData.setSucces(false);
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData("Send failed");
        }
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @GetMapping("/contacts")
    public ResponseEntity<?> contacts(){

        ResponseData responseData = new ResponseData();
        responseData.setSucces(true);
        responseData.setData(contactService.getListContact());
        responseData.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }


}
