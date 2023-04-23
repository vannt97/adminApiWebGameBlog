package com.example.adminApiWebGameBlog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/test/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasAnyRole('ROLE_ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/test/anonymous")
    @PreAuthorize("hasRole('ROLE_ANONYMOUS') or hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_USER')")
    public String moderatorAccess1() {
        return "Moderator Board.";
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

}
