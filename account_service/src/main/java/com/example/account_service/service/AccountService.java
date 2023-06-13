package com.example.account_service.service;

import com.example.account_service.model.Account;
import com.example.account_service.model.Transaction;
import com.example.account_service.model.dto.TransactionDto;
import com.example.account_service.repo.AccountRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

        // Calculate interest
        BigDecimal interestRate = new BigDecimal("0.05"); // interest rate of 5%
        int timePeriodInMonths = acount.getTime_period();
        BigDecimal balance = acount.getBalance();
        BigDecimal interest = balance.multiply(interestRate).multiply(new BigDecimal(timePeriodInMonths));

        // Add interest to balance
        BigDecimal updatedBalance = balance.add(interest);

        acount.setBalance(updatedBalance);
        acount.setAccount_number(accountNumber);
        accountRepo.saveLoanAccount(acount);
    }

    public void saveFixedAccount(Account acount) {
        // Calculate interest
        BigDecimal interestRate = new BigDecimal("0.05"); // interest rate of 5%
        int timePeriodInMonths = acount.getTime_period();
        BigDecimal balance = acount.getBalance();
        BigDecimal interest = balance.multiply(interestRate).multiply(new BigDecimal(timePeriodInMonths));

        // Add interest to balance
        BigDecimal updatedBalance = balance.add(interest);

        String accountNumber = generateRandomAccountNumber();
        acount.setAccount_number(accountNumber);
        acount.setBalance(updatedBalance);
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

    @Transactional
    public String transaction(TransactionDto transactionDto) {
        try {
            Account account = accountRepo.searchAccount(transactionDto.getAccount_number());
            if (account != null) {
                if (account.getAccount_type().equals("saving")) {
                    if (transactionDto.getTranType().equals("deposit")) {
                        BigDecimal balance = account.getBalance().add(transactionDto.getAmount());
                        accountRepo.updateDepositBalance(transactionDto.getAccount_number(), balance);
                        saveTransactionRecord(account.getAccount_id(), "Deposit", transactionDto.getAmount());
                        return "deposit successfully";
                    } else {
                        if (transactionDto.getAmount().compareTo(account.getBalance()) <= 0) {
                            BigDecimal balance = account.getBalance().subtract(transactionDto.getAmount());
                            accountRepo.updateDepositBalance(transactionDto.getAccount_number(), balance);
                            saveTransactionRecord(account.getAccount_id(), "Withdrawal", transactionDto.getAmount());
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
                } else {
                    return "Please enter savings account";
                }

            } else {
                return "loan account-exits";
            }

        } catch (Exception e) {
            throw e;
        }
    }


    public int calculateInterest() {
        //search all savings accounts
        List<Account> savingsAccounts = accountRepo.findByAccountType("saving");
        int count = 0;

        for (Account account : savingsAccounts) {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal interestAmount = currentBalance.multiply(BigDecimal.valueOf(0.05));
            BigDecimal updatedBalance = currentBalance.add(interestAmount);
            account.setBalance(updatedBalance);

            accountRepo.updateAccountBalance(account);

            // Add transaction record
            Transaction transaction = new Transaction();
            transaction.setAccount_id(account.getAccount_id());
            transaction.setSource("Interest Calculation");
            transaction.setTransaction_type("Interest");
            transaction.setAmount(interestAmount);
            transaction.setCreated_at(LocalDateTime.now());

            // Save the transaction
            accountRepo.saveTransaction(transaction);
            count++;
        }
        return count;
    }

    private void saveTransactionRecord(int accountNumber, String transactionType, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount_id(accountNumber);
        transaction.setTransaction_type(transactionType);
        transaction.setAmount(amount);
        transaction.setSource("Transactions");
        transaction.setCreated_at(LocalDateTime.now());

        accountRepo.saveTransaction(transaction);
    }
}
