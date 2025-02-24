package com.example.Spring.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Spring.Entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{}
