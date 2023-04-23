package com.example.adminApiWebGameBlog.controller;

import com.example.adminApiWebGameBlog.dto.UserDetailsImpl;
import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.entity.RoleEntity;
import com.example.adminApiWebGameBlog.jwt.JwtTokenHelper;
import com.example.adminApiWebGameBlog.jwt.TypeToken;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import com.example.adminApiWebGameBlog.payload.request.SigninRequest;
import com.example.adminApiWebGameBlog.payload.request.SignupRequest;
import com.example.adminApiWebGameBlog.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
public class LoginController {

     @Autowired
     JwtTokenHelper tokenHelper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("signin")
    public ResponseEntity<?> sigin(@RequestBody SigninRequest signinRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseData responseData = new ResponseData();
        Map<String,Object> mapToken = new HashMap<>();
        mapToken.put("username",signinRequest.getUsername());
        mapToken.put("typeToken", TypeToken.TOKEN);
        Map<String,Object> mapRefeshToken = new HashMap<>();
        mapRefeshToken.put("username",signinRequest.getUsername());
        mapRefeshToken.put("typeToken",TypeToken.RESFESH_TOKEN);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //giai han 12 tieng
            long expiredDateToken = 12 * 60 * 60 * 1000;
            String token = tokenHelper.generateToken(objectMapper.writeValueAsString(mapToken), expiredDateToken);
            //giai han 8 ngay
            long expiredDateRefeshToken = 80 * 60 * 60 * 1000;
            String refeshToken = tokenHelper.generateToken(objectMapper.writeValueAsString(mapRefeshToken), expiredDateRefeshToken);
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            data.put("refeshToken",refeshToken);
            data.put("username",signinRequest.getUsername());
            data.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setSucces(true);
            responseData.setData(data);
        }catch (Exception e){
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setSucces(true);
            responseData.setData("Lỗi convert json");
        }
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("signup")
    public ResponseEntity<?>  registerAccount(@RequestBody SignupRequest signupRequest){
        if(accountService.checkLogin(signupRequest.getUsername()).size() > 0){
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }else {
            accountService.saveUser2(signupRequest.getUsername(),signupRequest.getPassword(),signupRequest.getRole());
        }
        return ResponseEntity.ok("User registered successfully!");
    }
}