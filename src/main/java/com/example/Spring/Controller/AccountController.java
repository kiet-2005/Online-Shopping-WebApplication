package com.example.Spring.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
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

    @PutMapping("/update/accounts/{username}")
    public ResponseEntity<Account> updateAccountStory(@PathVariable String username, @RequestBody Account account) {
    if (!dao.existsById(username)) {
        return ResponseEntity.notFound().build();
    }

    System.out.println("Account" + account);

    // Lấy tài khoản từ database
    Account existingAccount = dao.findById(username).orElse(null);
    if (existingAccount == null) {
        return ResponseEntity.notFound().build();
    }

    // Cập nhật thông tin mới, giữ lại giá trị cũ nếu không có dữ liệu mới
    existingAccount.setFullname(account.getFullname() != null ? account.getFullname() : existingAccount.getFullname());
    existingAccount.setEmail(account.getEmail() != null ? account.getEmail() : existingAccount.getEmail());
    existingAccount.setPhoto(account.getPhoto() != null ? account.getPhoto() : existingAccount.getPhoto());

    // Chỉ mã hóa password nếu có dữ liệu mới
    if (account.getPassword() != null && !account.getPassword().isEmpty()) {
        existingAccount.setPassword(passwordEncoder.encode(account.getPassword()));
    }

    // Lưu lại thông tin mới
    return ResponseEntity.ok(dao.save(existingAccount));
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

    @GetMapping("/accounts/profile/google")
    public Map<String, Object> getUserInfoGoogle(OAuth2AuthenticationToken token) {
        Map<String, Object> userInfo = token.getPrincipal().getAttributes();
        Map<String, Object> response = new HashMap<>();
        response.put("name", userInfo.get("name"));
        response.put("email", userInfo.get("email"));
        response.put("avatar", userInfo.get("picture"));
        System.out.println("User info: " + response);
        return response;
    }
}
