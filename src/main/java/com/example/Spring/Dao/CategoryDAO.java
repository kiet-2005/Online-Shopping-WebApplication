package com.example.Spring.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Spring.Entity.Category;

public interface CategoryDAO extends JpaRepository<Category, String>{}
