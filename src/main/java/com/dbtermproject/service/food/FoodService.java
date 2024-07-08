package com.dbtermproject.service.food;

import com.dbtermproject.dto.search.mainsearch.MainSearchRequest;
import com.dbtermproject.entity.Food;
import com.dbtermproject.repository.food.FoodRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    // 메인 페이지에서 음식 목록을 조회하는 메소드, 검색 조건이 없으면 전체 조회,
    // 특정 조건만 존재해도 검색할 수 있도록 작성
    @Transactional
    public List<Food> getFoodList(MainSearchRequest mainSearchRequest) {
        if (mainSearchRequest.getCategory() == null &&
                mainSearchRequest.getKeyword() == null &&
                mainSearchRequest.getMinPrice() == null &&
                mainSearchRequest.getMaxPrice() == null) {
            return foodRepository.findAll();
        } else {
            String category = mainSearchRequest.getCategory();
            String keyword = (mainSearchRequest.getKeyword() == null ||
                    mainSearchRequest.getKeyword().isEmpty()) ? null : mainSearchRequest.getKeyword();
            String minPrice = (mainSearchRequest.getMinPrice() == null ||
                    mainSearchRequest.getMinPrice().isEmpty()) ? null : mainSearchRequest.getMinPrice();
            String maxPrice = (mainSearchRequest.getMaxPrice() == null ||
                    mainSearchRequest.getMaxPrice().isEmpty()) ? null : mainSearchRequest.getMaxPrice();
            return foodRepository.findSearchItem(category, keyword, minPrice, maxPrice);
        }
    }
}
