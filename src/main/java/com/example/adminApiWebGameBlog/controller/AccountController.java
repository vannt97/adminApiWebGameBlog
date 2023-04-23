package com.example.adminApiWebGameBlog.controller;

import com.example.adminApiWebGameBlog.dto.EditAccountDTO;
import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.jwt.JwtTokenHelper;
import com.example.adminApiWebGameBlog.jwt.TypeToken;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import com.example.adminApiWebGameBlog.payload.request.SigninRequest;
import com.example.adminApiWebGameBlog.payload.request.SignupRequest;
import com.example.adminApiWebGameBlog.service.AccountService;
import com.example.adminApiWebGameBlog.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class AccountController {

        @Autowired
        AccountService accountService;

        @Autowired
        RoleService roleService;

        @GetMapping("accounts")
        public ResponseEntity<?> getAccounts(){
            ResponseData response = new ResponseData();
            try {
                response.setSucces(true);
                response.setStatus(HttpStatus.OK.value());
                response.setData(accountService.getList());
            }catch (Exception e){
                response.setSucces(false);
                response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
                response.setData("Khong co du lieu");
            }
            return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
        }

        @GetMapping("count/accounts")
        public ResponseEntity<?> getSizeAccount(){
            ResponseData response = new ResponseData();
            try {
                response.setSucces(true);
                response.setStatus(HttpStatus.OK.value());
                Map<String, Object> data = new HashMap<>();
                data.put("size", accountService.sizeAccount());
                response.setData(data);
            }catch (Exception e){
                response.setSucces(false);
                response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
                response.setData(e.getMessage());
            }
            return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
        }

        @PutMapping("create/account")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createAccount(@RequestBody SignupRequest signupRequest){
            ResponseData responseData = new ResponseData();
            if(accountService.checkLogin(signupRequest.getUsername()).size() > 0){
                responseData.setSucces(false);
                responseData.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
                responseData.setData(null);
                return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
            }else {
                accountService.saveUser2(signupRequest.getUsername(),signupRequest.getPassword(),signupRequest.getRole());
            }
            return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
        }

        @DeleteMapping("delete/{id}/account")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable long id){
            ResponseData responseData = new ResponseData();
            if(accountService.deleteAccount(id)){
                responseData.setSucces(true);
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setData("Xoá thành công");
            }else {
                responseData.setSucces(false);
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setData("Xoá thất bại");
            }
            return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
        }


        @PostMapping("edit/account")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editAccount(@RequestBody EditAccountDTO editAccount){
            ResponseData responseData = new ResponseData();
            if(accountService.editAccount(editAccount)){
                responseData.setSucces(true);
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setData("Edit thành công");
            }else {
                responseData.setSucces(false);
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setData("Edit thất bại");
            }
            return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
        }
}
