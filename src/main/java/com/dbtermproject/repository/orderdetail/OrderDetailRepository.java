package com.dbtermproject.repository.orderdetail;

import com.dbtermproject.dto.orderdetail.OrderDetailId;
import com.dbtermproject.entity.Food;
import com.dbtermproject.entity.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    @Query("SELECT c FROM OrderDetail c where c.cart.id = :cartId")
    List<OrderDetail> findAllById(int cartId);

    @Query("SELECT c.food FROM OrderDetail c where c.cart.id = :cartId")
    List<Food> findFoodById(int cartId);

    @Query("SELECT c.quantity FROM OrderDetail c where c.cart.id = :cartId")
    List<Integer> findQuantityById(int cartId);
}
