package com.example.adminApiWebGameBlog.sercurity;

import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    AccountService accountService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<AccountEntity> accounts = accountService.checkLogin(username);
        if(accounts.size() > 0){
            if(passwordEncoder.matches(password,accounts.get(0).getPassword())){
                return new UsernamePasswordAuthenticationToken(username,password,new ArrayList<>());
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
