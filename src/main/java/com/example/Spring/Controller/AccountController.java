package com.example.Spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.AccountDAO;
import com.example.Spring.Entity.Account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountDAO dao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return dao.findAll();
    }
    
    @PostMapping("/admin/accounts/create")
    public ResponseEntity<Account> createAccount (@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return ResponseEntity.ok(dao.save(account));
    }

    @PutMapping("/admin/accounts/{username}")
    public ResponseEntity<Account> updateAccount (@PathVariable String username, @RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (!dao.existsById(username)) return ResponseEntity.notFound().build();
        account.setUsername(username);
        return ResponseEntity.ok(dao.save(account)); 
    }

    @DeleteMapping("/admin/accounts/{username}")
    public ResponseEntity<Void> deleteAccount (@PathVariable String username) {
        if (!dao.existsById(username)) return ResponseEntity.notFound().build();
        dao.deleteById(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts/profile")
    public Account getAccounts(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException("You need to login to view this page");
        }
        String username = authentication.getName();
        return dao.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }   
}
