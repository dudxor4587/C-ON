package com.dbtermproject.service.customer;

import com.dbtermproject.entity.Customer;
import com.dbtermproject.repository.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    // 로그인 메소드, 비밀번호가 일치하지 않으면 예외 메세지 전송
    @Transactional
    public void login(String cno, String password) {
        Customer customer = customerRepository.findById(cno).orElseThrow();
        try {
            customer.login(password);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 회원 이름을 조회하는 메소드
    @Transactional
    public String getName(String cno) {
        return customerRepository.findById(cno).get().getName();
    }
}
