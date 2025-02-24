package com.example.Spring.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Spring.Entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{}
