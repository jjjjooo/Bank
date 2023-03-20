package com.example.bank.accout.domain;

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
}


