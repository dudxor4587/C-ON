package com.dbtermproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Customer {
    @Id
    private String cno;

    private String name;

    private String password;

    private String phoneNumber;

    public void login(String password) {
        if (password == null || !password.equals(this.password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
