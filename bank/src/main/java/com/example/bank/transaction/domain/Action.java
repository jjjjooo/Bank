package com.example.bank.transaction.domain;

import com.example.bank.accout.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "action_tb")
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount;
    private Long amount;
    private Long withdrawAccountBalance;
    private Long depositAccountBalance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String sender;
    private String receiver;
    private Long tel;
    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;
    @LastModifiedDate //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Action(Long id, Account withdrawAccount, Account depositAccount, Long amount,
                  Long withdrawAccountBalance, Long depositAccountBalance, TransactionType transactionType,
                  String sender, String receiver, Long value, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.transactionType = transactionType;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = value;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
