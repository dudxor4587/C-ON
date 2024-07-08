package com.dbtermproject.repository.customer;

import com.dbtermproject.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "SELECT c.cno AS customerId, c.name AS customerName, COUNT(DISTINCT ca.id) AS orderCount, COALESCE(SUM(od.totalPrice), 0) AS totalPrice " +
            "FROM Customer c " +
            "LEFT JOIN Cart ca ON c.cno = ca.cno AND ca.orderDate IS NOT NULL " +
            "LEFT JOIN OrderDetail od ON ca.id = od.id " +
            "WHERE c.cno != 'C0' " +
            "GROUP BY c.cno, c.name", nativeQuery = true)
    List<Object[]> findMemberOrderStats();

}

