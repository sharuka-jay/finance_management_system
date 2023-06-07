package com.example.account_service.service;

import com.example.account_service.model.Account;
import com.example.account_service.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;
    public void saveAccount(Account acount) {
        String accountNumber = generateRandomAccountNumber();
        acount.setAccount_number(accountNumber);
        accountRepo.saveAccount(acount);

    }

    public String generateRandomAccountNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // Generate a 6-digit random number
        return "111" + randomNumber;
    }

    public void saveLoanAccount(Account acount) {
        String accountNumber = generateRandomAccountNumber();
        acount.setAccount_number(accountNumber);
        accountRepo.saveLoanAccount(acount);
    }

    public void saveFixedAccount(Account acount) {
        String accountNumber = generateRandomAccountNumber();
        acount.setAccount_number(accountNumber);
        accountRepo.saveFixedAccount(acount);
    }

    public String tranfer(double balance, String tranType, int accountNumber) {
//        double account_balance =  accountRepo.searchBalance(accountNumber);
        if (tranType.equals("deposit")){
           accountRepo.depositMoney(balance,accountNumber);
           return "done";
        }else {
            return "wrong";
        }
    }
}
