package com.dbtermproject.repository.cart;

import com.dbtermproject.entity.Cart;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT c FROM Cart c WHERE c.customer.cno = :cno AND c.orderDate IS NULL")
    Optional<Cart> findCart(@Param("cno") String cno);

    @Query("SELECT c FROM Cart c WHERE c.customer.cno = :cno AND c.orderDate IS NOT NULL")
    Optional<List<Cart>> findPaidCart(@Param("cno") String cno);

    @Query("SELECT c FROM Cart c WHERE c.customer.cno = :cno AND DATE(c.orderDate) BETWEEN :startDate AND :endDate")
    Optional<List<Cart>> findCartByDate(@Param("cno") String cno, @Param("startDate") LocalDate startDate, @Param("endDate")
    LocalDate endDate);
}
