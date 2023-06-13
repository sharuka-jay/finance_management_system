package com.example.interest_service.service;

import com.example.interest_service.repo.InterestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    @Autowired
    InterestRepo interestRepo;
    public void calculateInterest() {
        //get all savings accounts
//        List<Account> savingsAccounts = accountRepository.findByAccountType("savings");
//
//        for (Account account : savingsAccounts) {
//            BigDecimal currentBalance = account.getBalance();
//            BigDecimal interestAmount = currentBalance.multiply(BigDecimal.valueOf(0.05));
//            BigDecimal updatedBalance = currentBalance.add(interestAmount);
//            account.setBalance(updatedBalance);
//
//            accountRepository.updateAccountBalance(account);
//        }
    }
}
