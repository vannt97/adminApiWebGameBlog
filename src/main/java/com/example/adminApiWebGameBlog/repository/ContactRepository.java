package com.example.adminApiWebGameBlog.repository;

import com.example.adminApiWebGameBlog.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, CrudRepository<Contact,Long> {

}
