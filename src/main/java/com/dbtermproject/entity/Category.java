package com.dbtermproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Category {
    @Id
    private String categoryName;

    private int noOfFoods;
}
