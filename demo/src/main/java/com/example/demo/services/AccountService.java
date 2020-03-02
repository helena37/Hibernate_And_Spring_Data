package com.example.demo.services;

import com.example.demo.models.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccount(long accountId);
    void withdrawMoney(long accountId, BigDecimal amount);
    void depositMoney(long accountId, BigDecimal amount);
    void transferMoney(long fromId, long toId, BigDecimal amount);
}
