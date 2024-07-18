package com.dbtermproject.service.admin;

import com.dbtermproject.dto.customer.response.CustomerOrderResponse;
import com.dbtermproject.entity.Food;
import com.dbtermproject.repository.customer.CustomerRepository;
import com.dbtermproject.repository.food.FoodRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FoodRepository foodRepository;
    private final CustomerRepository customerRepository;

    // 관리자 페이지에서 회원 별 주문 횟수와 주문 금액을 조회하는 메소드
    @Transactional
    public List<CustomerOrderResponse> getCustomerOrderList() {
        List<Object[]> results = customerRepository.findMemberOrderStats();
        return results.stream()
                .map(result -> new CustomerOrderResponse(
                        (String) result[0],
                        (String) result[1],
                        ((Number) result[2]).intValue(),
                        ((Number) result[3]).intValue()
                ))
                .collect(Collectors.toList());
    }

    // 관리자 페이지에서 음식 판매량 순으로 상위 5개의 음식을 조회하는 메소드
    @Transactional
    public List<Food> getFoodList() {
        return foodRepository.findTop5ByOrderByFoodSalesDesc();
    }
}
