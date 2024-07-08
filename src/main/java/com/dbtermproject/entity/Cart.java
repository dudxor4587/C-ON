package com.dbtermproject.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nullable
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "cno")
    private Customer customer;

    public Cart(LocalDateTime orderDate, Customer customer) {
        this.orderDate = orderDate;
        this.customer = customer;
    }
    public void updateOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
