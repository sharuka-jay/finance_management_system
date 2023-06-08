package com.example.account_service.service;

import com.example.account_service.model.Account;
import com.example.account_service.model.dto.TransactionDto;
import com.example.account_service.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
        if (tranType.equals("deposit")) {
            accountRepo.depositMoney(balance, accountNumber);
            return "done";
        } else {
            return "wrong";
        }
    }


    public String transaction(TransactionDto transactionDto) {
        try {
            Account account = accountRepo.searchAccount(transactionDto.getAccount_number());
            if (account != null) {
                if (account.getAccount_type().equals("saving")) {
                    if (transactionDto.getTranType().equals("deposit")) {
                        BigDecimal balance = account.getBalance().add(transactionDto.getAmount());
                        accountRepo.updateDepositBalance(transactionDto.getAccount_number(), balance);
                        return "deposit successfully";
                    } else {
                        if (transactionDto.getAmount().compareTo(account.getBalance()) <= 0) {
                            BigDecimal balance = account.getBalance().subtract(transactionDto.getAmount());
                            accountRepo.updateDepositBalance(transactionDto.getAccount_number(), balance);
                            return "withdraw successfully";
                        } else {
                            return "insufficient balance";
                        }

                    }

                } else {
                    return "Please enter savings account";
                }
            } else {
                return "account-exits";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String repayLoan(TransactionDto transactionDto) {
        try {
            Account account = accountRepo.searchAccount(transactionDto.getAccount_number());
            if (account != null) {
                if (account.getAccount_type().equals("loan")) {
                    if (transactionDto.getAmount().compareTo(account.getBalance()) > 0) {
                        return "you are trying to pay more";
                    } else {
                        BigDecimal balance = account.getBalance().subtract(transactionDto.getAmount());
                        accountRepo.updateDepositBalance(transactionDto.getAccount_number(), balance);
                        return "payment success";

                    }
                }else {
                    return "Please enter savings account";
                }

            } else {
                return "loan account-exits";
            }

        } catch (Exception e) {
            throw e;
        }
    }


}
