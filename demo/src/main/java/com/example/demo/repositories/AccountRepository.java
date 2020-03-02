package com.example.demo.repositories;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(long id);
}
