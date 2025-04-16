package com.example.Spring.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Spring.Entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);

    @Query("SELECT SUM(od.quantity * od.price) FROM OrderDetail od WHERE od.order.id = :orderId")
    Double calculateTotalAmountByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT od.product, od.quantity, (od.price * od.quantity), o.status " +
            "FROM OrderDetail od " +
            "JOIN od.order o " +
            "WHERE o.account.username = :username")
    List<Object[]> findProductsByUsername(@Param("username") String username);

    @Query("SELECT u.fullname, o.account.username, SUM(od.price) FROM Order o JOIN o.orderDetails od JOIN o.account u GROUP BY u.fullname, o.account.username ORDER BY SUM(od.price) DESC")
    List<Object[]> findTop10UsersByTotalSpent();

}
