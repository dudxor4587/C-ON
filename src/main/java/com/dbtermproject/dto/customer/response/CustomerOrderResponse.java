package com.dbtermproject.dto.customer.response;

public record CustomerOrderResponse(String customerId, String customerName, int orderCount, int totalPrice) {
}
