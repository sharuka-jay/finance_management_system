package com.example.account_service.repo;

import com.example.account_service.model.Account;
import com.example.account_service.model.Transaction;
import com.example.account_service.model.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepo {
    void saveAccount(Account acount);

    void saveLoanAccount(Account acount);

    void saveFixedAccount(Account acount);

    void depositMoney(double balance, int accountNumber);

    Account searchAccount(String account_number);

    void updateDepositBalance(String account_number, BigDecimal balance);

    List<Account> getUserAccounts(int user_id);

    Account getUserAccount(String account_number);

    List<Account> findByAccountType(String savings);

    void updateAccountBalance(Account account);

    void saveTransaction(Transaction transaction);

    List<Transaction> tranList(int user_id);
}
