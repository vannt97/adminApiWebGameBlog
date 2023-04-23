package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.dto.EditAccountDTO;
import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.entity.RoleEntity;
import com.example.adminApiWebGameBlog.enums.ERole;
import com.example.adminApiWebGameBlog.repository.AccountRepository;
import com.example.adminApiWebGameBlog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<AccountEntity> getList(){
        return accountRepository.findAll();
    }

    public List<AccountEntity> checkLogin(String username){
        return accountRepository.findByUsername(username);
    }

    @Transactional
    public AccountEntity saveUser(String username, String password){
        try {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setUsername(username);
            String passwordEncode = encoder.encode(password);
            accountEntity.setPassword(passwordEncode);
            return accountRepository.save(accountEntity);
        }catch (Exception e){
            return null;
        }
    }

    @Transactional
    public void saveUser2(String username, String password, String strRoles){
        try {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setUsername(username);
            String passwordEncode = encoder.encode(password);
            accountEntity.setPassword(passwordEncode);
            accountEntity.setCreatedDate((new Date()));
            accountEntity.setModifiedDate((new Date()));
            RoleEntity role;
            if (strRoles == null) {
                role = roleRepository.findByName(ERole.ROLE_ANONYMOUS)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }else {
                if ("admin".equals(strRoles)) {
                    role = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                } else if ("anonymous".equals(strRoles)) {
                    role = roleRepository.findByName(ERole.ROLE_ANONYMOUS)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                } else {
                    role = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                }
            }
            accountEntity.setRole(role);
            accountRepository.save(accountEntity);
        }catch (Exception e){

        }
    }

    public long sizeAccount(){
        return accountRepository.getSizeAccount();
    }

    public boolean deleteAccount(long id){
        try {
            accountRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean editAccount(EditAccountDTO editAccount){
        Optional<AccountEntity> accountEdit = accountRepository.findById(editAccount.getId());

        if(accountEdit.isPresent()){
            RoleEntity role;
            if (editAccount.getRole() == null) {
                role = roleRepository.findByName(ERole.ROLE_ANONYMOUS)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }else {
                if ("admin".equals(editAccount.getRole())) {
                    role = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                } else if ("anonymous".equals(editAccount.getRole())) {
                    role = roleRepository.findByName(ERole.ROLE_ANONYMOUS)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                } else {
                    role = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                }
            }
            accountEdit.get().setModifiedDate((new Date()));
            accountEdit.get().setRole(role);
            String passwordEncode = encoder.encode(editAccount.getPassword());
            accountEdit.get().setPassword(passwordEncode);
            accountRepository.save(accountEdit.get());
            return true;
        }else {
            return false;
        }
    }
}
