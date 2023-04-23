package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.entity.Contact;
import com.example.adminApiWebGameBlog.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public List<Contact> getListContact(){
        return contactRepository.findAll();
    }

    public boolean saveContact(String name, String email, String subject, String message){
        try {
            Contact contactnew = new Contact(email,name,subject,message);
            contactRepository.save(contactnew);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
