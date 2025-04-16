package com.example.Spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.AccountDAO;
import com.example.Spring.Dto.RegisterDTO;
import com.example.Spring.Entity.Account;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class RegisterController {
    @Autowired
    AccountDAO dao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return ResponseEntity.ok(dao.save(account));
    }

    // @PostMapping("/register")
    // public ResponseEntity<?> createAccount(@Valid @RequestBody RegisterDTO dto,
    // BindingResult result) {
    // if (result.hasErrors()) {
    // return
    // ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
    // }

    // if (!dto.getPassword().equals(dto.getPasswordCheck())) {
    // return ResponseEntity.badRequest().body("Password không khớp!");
    // }
    // if (dao.existsById(dto.getUsername())) {
    // return ResponseEntity.badRequest().body("Username đã tồn tại!");
    // }

    // Account account = new Account();
    // account.setUsername(dto.getUsername());
    // account.setPassword(passwordEncoder.encode(dto.getPassword()));
    // account.setFullname(dto.getFullname());
    // account.setEmail(dto.getEmail());
    // account.setActivated(true);
    // account.setAdmin(false);

    // return ResponseEntity.ok(dao.save(account));
    // }

}
