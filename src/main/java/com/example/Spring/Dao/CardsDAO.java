package com.example.Spring.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Spring.Entity.Cards;

import jakarta.transaction.Transactional;

public interface CardsDAO extends JpaRepository<Cards, Long> {
    @Query("SELECT c.product, COUNT(c) as quantity FROM Cards c WHERE c.user.username = :username GROUP BY c.product")
    List<Object[]> findCartProductsByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cards c WHERE c.product.id = :productId AND c.user.username = :username")
    void deleteByProductIdAndUsername(@Param("productId") Long productId, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cards c WHERE c.user.username = :username")
    void deleteByUsername(@Param("username") String username);

    List<Cards> findByUserUsername(String username);
}
