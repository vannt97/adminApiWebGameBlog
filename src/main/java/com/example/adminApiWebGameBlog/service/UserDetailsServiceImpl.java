package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.dto.UserDetailsImpl;
import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userCustomService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AccountEntity> accounts = accountRepository.findByUsername(username);
        if(accounts.size() > 0){
            UserDetails userDetails = UserDetailsImpl.build(accounts.get(0));
            return userDetails;
        }else {
            return (UserDetails) new UsernameNotFoundException("User Not Found with username: " + username);
        }
    }
}
