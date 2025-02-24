package com.example.Spring.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Spring.Entity.Account;

public interface AccountDAO extends JpaRepository<Account, String>{
    Optional<Account> findByUsername (String username);
}