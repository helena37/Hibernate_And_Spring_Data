package com.example.demo.services;

import com.example.demo.exceptions.IllegalBankOperationException;
import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void withdrawMoney(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalBankOperationException("Current balance: $" + account.getBalance()
            + " is not sufficient to withdraw amount: " + amount);
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Override
    public void depositMoney(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void transferMoney(long fromId, long toId, BigDecimal amount) {
        depositMoney(toId, amount);
        withdrawMoney(fromId, amount);
    }
}
