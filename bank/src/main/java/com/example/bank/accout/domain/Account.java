package com.example.bank.accout.domain;

import com.example.bank.global.exception.CustomApiException;
import com.example.bank.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_tb")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private Long number;
    @Column(nullable = false, length = 4)
    private Long password;
    @Column(nullable = false)
    private Long balance;

    @ManyToOne(fetch =  FetchType.LAZY)
    private User user;
    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;
    @LastModifiedDate //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user,
                   LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public void checkOwner(Long userId){
        if(user.getId() != userId){
            throw new CustomApiException("계좌를 찾을 수 없습니다.");
        }
    }

    public void deposit(Long amount) {
        this.balance += amount;
    }

    public void checkPassword(Long password) {
        if(this.password.longValue() != password){
            throw new CustomApiException("게좌 비밀번호 검증에 실패했습니다");
        }
    }

    public void checkBalance(Long amount) {
        if(this.balance < amount){
            throw new CustomApiException("계좌잔액이 부족합니다.");
        }
    }

    public void withdraw(Long amount) {
        this.checkBalance(amount);
        this.balance -= amount;
    }
}


