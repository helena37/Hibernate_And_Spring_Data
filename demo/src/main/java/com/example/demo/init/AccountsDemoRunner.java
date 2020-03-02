package com.example.demo.init;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class AccountsDemoRunner implements ApplicationRunner {

    private UserService userService;
    private AccountService accountService;

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User("Ivan Petrov", 35);
        User user2 = new User("Stamat Dimitrov", 49);

        Account account1 = new Account(new BigDecimal(5200), user1);
        user1.getAccounts().add(account1);

        Account account2 = new Account(new BigDecimal(35000), user2);
        user2.getAccounts().add(account2);

        userService.registerUser(user1);
        userService.registerUser(user2);

        accountService.withdrawMoney(account1.getId(), new BigDecimal(500));
        accountService.depositMoney(account1.getId(), new BigDecimal(200));
        accountService.transferMoney(account1.getId(), account2.getId(), new BigDecimal(5000));
    }
}
