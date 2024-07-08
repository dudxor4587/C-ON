package com.dbtermproject.repository.food;

import com.dbtermproject.entity.Food;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodRepository extends JpaRepository<Food, String> {

    @Query(value = "SELECT * FROM (" +
            "    SELECT f.*, ROW_NUMBER() OVER (ORDER BY SUM(od.quantity) DESC) AS rn " +
            "    FROM Food f " +
            "    JOIN OrderDetail od ON f.foodName = od.foodName " +
            "    JOIN Cart c ON od.id = c.id " +
            "    WHERE c.orderDate IS NOT NULL " +
            "    GROUP BY f.foodName, f.price " +
            ") subquery " +
            "WHERE rn <= 5", nativeQuery = true)
    List<Food> findTop5ByOrderByFoodSalesDesc();

    @Query(value = "SELECT DISTINCT f.* FROM Food f " +
            "JOIN Contains c ON c.foodName = f.foodName " +
            "WHERE (:category = '전체' OR c.categoryName = :category) " +
            "AND (:keyword IS NULL OR f.foodName LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:minPrice IS NULL OR f.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR f.price <= :maxPrice)", nativeQuery = true)
    List<Food> findSearchItem(@Param("category") String category,
                              @Param("keyword") String keyword,
                              @Param("minPrice") String minPrice,
                              @Param("maxPrice") String maxPrice);
}
