package com.dbtermproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Food {
    @Id
    private String foodName;

    private int price;
}
