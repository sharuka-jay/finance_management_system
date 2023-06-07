package com.example.account_service.controller;

import com.example.account_service.model.Account;
import com.example.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

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
    public String transferFunds(@RequestBody Map<String, Object> requestBody) {
        try {

        } catch (Exception e) {
           throw e;

        }
        return "path";
    }


}
