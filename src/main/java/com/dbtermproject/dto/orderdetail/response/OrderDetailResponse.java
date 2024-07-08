package com.dbtermproject.dto.orderdetail.response;

import com.dbtermproject.entity.Food;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(LocalDateTime orderDate, List<Food> food, List<Integer> quantity, int totalPrice) {
}
