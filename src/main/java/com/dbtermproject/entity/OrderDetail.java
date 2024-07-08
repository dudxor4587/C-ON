package com.dbtermproject.entity;

import com.dbtermproject.dto.orderdetail.OrderDetailId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@IdClass(OrderDetailId.class)
public class OrderDetail {
    @Id
    @Column(name = "itemNo")
    private int itemNo;

    private int quantity;

    @Column(name = "totalPrice")
    private int totalPrice;

    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "foodName")
    private Food food;

    public OrderDetail(int itemNo, Cart cart, int quantity, int totalPrice, Food food) {
        this.itemNo = itemNo;
        this.cart = cart;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.food = food;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
        this.totalPrice += this.food.getPrice() * quantity;
    }

    public void plusQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.food.getPrice() * quantity;
    }

    public void minusQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.food.getPrice() * quantity;
    }
}
