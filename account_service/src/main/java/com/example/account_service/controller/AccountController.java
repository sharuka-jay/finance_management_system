package com.example.account_service.controller;

import com.example.account_service.model.Account;
import com.example.account_service.model.Transaction;
import com.example.account_service.model.dto.TransactionDto;
import com.example.account_service.repo.AccountRepo;
import com.example.account_service.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepo accountRepo;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    @PostMapping("/savingaccounts")
    public String saveSavingAccount(@RequestBody Account acount) {
        acount.setAccount_type("saving");
        accountService.saveAccount(acount);
//        String path = "redirect:/home?param="+acount.getUserId().toString();
        return "path";
    }

    @PostMapping("/loanAccount")
    public String saveLoanAccount(@RequestBody Account acount) {
        acount.setAccount_type("loan");
        accountService.saveLoanAccount(acount);
//        String path = "redirect:/home?param="+acount.getUserId().toString();
        return "path";
    }

    @PostMapping("/newfixedaccount")
    public String saveFixedAccount(@RequestBody Account acount) {
        acount.setAccount_type("fixed");
        accountService.saveFixedAccount(acount);
        return "path";
    }

    /**  transactions  */
    @PostMapping("/transfer")
    public String transactions(@RequestBody TransactionDto transactionDto) {
        try {
            String res = accountService.transaction(transactionDto);
            return res;

        } catch (Exception e) {
           throw e;
        }
    }

    @PostMapping("/repay")
    public String repayLoans(@RequestBody TransactionDto transactionDto) {
        try {
            String res = accountService.repayLoan(transactionDto);
            return res;

        } catch (Exception e) {
            throw e;
        }
    }


    /**  search accounts by user id to display home  */
    @PostMapping("/seachAllAccountsbyUserID")
    public List<Account> searchAccounts(@RequestBody TransactionDto transactionDto) {
        try {
             List<Account> account = accountRepo.getUserAccounts(transactionDto.getUser_id());
             return account;


        } catch (Exception e) {
            throw e;
        }
    }

    /**  search account to display home  */
    @PostMapping("/seachAccount")
    public Account searchAccount(@RequestBody TransactionDto transactionDto) {
        try {
            Account account = accountRepo.getUserAccount(transactionDto.getAccount_number());
            return account;


        } catch (Exception e) {
            throw e;
        }
    }

    /*** view ttrans for selected account  ***/

    @PostMapping("/viewTrans")
    public List<Transaction> viewTransForSelectedAccounts(@RequestBody TransactionDto transactionDto) {
        try {
            List<Transaction> trans = accountRepo.tranList(transactionDto.getAccount_id());
            return trans;


        } catch (Exception e) {
            throw e;
        }
    }

    /************      calculate interest and add it     *****/
   @GetMapping("interest")
    public String calculateInterest() {
        try {
            int count = accountService.calculateInterest();
            logger.info("interest calculated for  "+ count + " accounts");
            return "interest calculated for  "+ count + " accounts";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to calculate interest";
        }
    }

}
