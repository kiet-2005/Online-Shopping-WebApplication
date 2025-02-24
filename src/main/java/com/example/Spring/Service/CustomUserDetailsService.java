package com.example.Spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Spring.Dao.AccountDAO;
import com.example.Spring.Entity.Account;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    AccountDAO dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = dao.findByUsername(username).orElse(null);
        String role = account.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), List.of(new SimpleGrantedAuthority(role)));
    }
}
