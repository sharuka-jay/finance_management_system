package com.example.account_service.repo;

import com.example.account_service.model.Account;

public interface AccountRepo {
    void saveAccount(Account acount);

    void saveLoanAccount(Account acount);

    void saveFixedAccount(Account acount);

    void depositMoney(double balance, int accountNumber);
}
