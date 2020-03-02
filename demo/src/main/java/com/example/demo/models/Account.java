package com.example.demo.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    private long id;
    private BigDecimal balance;
    private User user;

    public Account(BigDecimal balance, User user) {
        this.setBalance(balance);
        this.setUser(user);
    }

    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false, columnDefinition = "DECIMAL UNSIGNED")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
