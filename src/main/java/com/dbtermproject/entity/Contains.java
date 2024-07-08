package com.dbtermproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Contains {
    @Id
    @ManyToOne
    @JoinColumn(name = "foodName")
    private Food food;

    @Id
    @ManyToOne
    @JoinColumn(name = "categoryName")
    private Category category;
}
