package com.example.bank.user.domain;

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
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    @Column(nullable = false, length = 60)
    private String password;
    @Column(nullable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 20)
    private String fullName;
    @Column()
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;
    @LastModifiedDate //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullName,
                UserRole userRole, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.userRole = userRole;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
