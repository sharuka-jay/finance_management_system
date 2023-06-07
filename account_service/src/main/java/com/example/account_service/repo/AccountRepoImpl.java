package com.example.account_service.repo;

import com.example.account_service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class AccountRepoImpl implements AccountRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveAccount(Account account) {
        try {
            String sql = "INSERT INTO accounts ( account_number, account_type,user_id, balance, created_at) VALUES ( ?, ?, ?, ?, ?);";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            jdbcTemplate.update(sql
                    , account.getAccount_number()
//                , account.getAccount_name()
                    , account.getAccount_type()
                    ,account.getUser_id()
                    , account.getBalance()
                    ,createdAt
            );
        }catch (Exception e) {
             throw e;
        }

    }

    @Override
    public void saveLoanAccount(Account account) {
        try {
            String sql = "INSERT INTO accounts ( account_number, account_type,user_id, balance, created_at,time_period) VALUES ( ?,?, ?, ?, ?, ?);";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            jdbcTemplate.update(sql
                    , account.getAccount_number()
//                , account.getAccount_name()
                    , account.getAccount_type()
                    ,account.getUser_id()
                    , account.getBalance()
                    ,createdAt
                    ,account.getTime_period()
            );
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void saveFixedAccount(Account account) {
        try {
            String sql = "INSERT INTO accounts ( account_number, account_type,user_id, balance, created_at,time_period) VALUES ( ?,?, ?, ?, ?, ?);";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            jdbcTemplate.update(sql
                    , account.getAccount_number()
//                , account.getAccount_name()
                    , account.getAccount_type()
                    ,account.getUser_id()
                    , account.getBalance()
                    ,createdAt
                    ,account.getTime_period()
            );
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void depositMoney(double balance, int accountNumber) {
        try {
            String sql = "UPDATE accounts SET   balance=? WHERE account_number = ? ;";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            jdbcTemplate.update(sql,balance,accountNumber);
        }catch (Exception e) {
            throw e;
        }
    }
}
