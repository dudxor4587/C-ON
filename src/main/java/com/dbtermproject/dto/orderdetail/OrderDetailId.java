package com.dbtermproject.dto.orderdetail;

import com.dbtermproject.entity.Cart;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderDetailId implements Serializable {
    private int itemNo;
    private Cart cart;
}
