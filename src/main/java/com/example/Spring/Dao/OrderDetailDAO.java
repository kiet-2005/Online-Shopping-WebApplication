package com.example.Spring.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Spring.Entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
    List<OrderDetail> findByOrderId(Long orderId);

    @Query("SELECT SUM(od.quantity * od.price) FROM OrderDetail od WHERE od.order.id = :orderId")
    Double calculateTotalAmountByOrderId(@Param("orderId") Long orderId);
}
