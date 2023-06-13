package com.example.account_service.repo;

import com.example.account_service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                    , account.getUser_id()
                    , account.getBalance()
                    , createdAt
            );
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void saveLoanAccount(Account account) {
        try {
            String sql = "INSERT INTO accounts ( account_number, account_type,user_id, balance, created_at,time_period) VALUES ( ?,?, ?, ?, ?, ?);";
            Date currentDate = new Date();
            Timestamp createdAt = new Timestamp(currentDate.getTime());
            jdbcTemplate.update(sql
                    , account.getAccount_number()
//                , account.getAccount_name()
                    , account.getAccount_type()
                    , account.getUser_id()
                    , account.getBalance()
                    , createdAt
                    , account.getTime_period()
            );
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void saveFixedAccount(Account account) {
        try {
            String sql = "INSERT INTO accounts ( account_number, account_type,user_id, balance, created_at,time_period) VALUES ( ?,?, ?, ?, ?, ?);";
//            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime lockDate = createdAt.plusMonths(account.getTime_period());

            jdbcTemplate.update(sql
                    , account.getAccount_number()
//                , account.getAccount_name()
                    , account.getAccount_type()
                    , account.getUser_id()
                    , account.getBalance()
                    , createdAt
                    , account.getTime_period()
            );
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void depositMoney(double balance, int accountNumber) {
        try {
            String sql = "UPDATE accounts SET   balance=? WHERE account_number = ? ;";
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            jdbcTemplate.update(sql, balance, accountNumber);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Account searchAccount(String account_number) {
        Account account = null;
        try {
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            Map<String, Object> accountData = jdbcTemplate.queryForMap(sql, account_number);
            if (accountData != null && !accountData.isEmpty()) {
                account = new Account();
                account.setAccount_number((String) accountData.get("account_number"));
                account.setAccount_type((String) accountData.get("account_type"));
                account.setBalance((BigDecimal) accountData.get("balance"));
            }
            return account;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void updateDepositBalance(String account_number, BigDecimal balance) {
        try {

            String sql = "UPDATE `accounts` SET `balance`=?,`updated_at`=? WHERE `account_number`=?";
            Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
            int res = jdbcTemplate.update(sql, balance, updatedAt, account_number);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Account> getUserAccounts(int user_id) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try {
            List<Account> accounts = jdbcTemplate.query(
                    sql,
                    new Object[]{user_id},
                    (rs, rowNum) -> {
                        Account account = new Account();
                        account.setAccount_number(rs.getString("account_number"));
                        account.setUser_id(rs.getInt("user_id"));
                        account.setBalance(rs.getBigDecimal("balance"));
                        account.setTime_period(rs.getInt("time_period"));
                        account.setAccount_type(rs.getString("account_type"));
                        return account;
                    }
            );

            return accounts;
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Account getUserAccount(String account_number) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try {
            Account account = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{account_number},
                    (rs, rowNum) -> {
                        Account acc = new Account();
                        acc.setAccount_number(rs.getString("account_number"));
                        acc.setUser_id(rs.getInt("user_id"));
                        acc.setBalance(rs.getBigDecimal("balance"));
                        acc.setTime_period(rs.getInt("time_period"));
                        acc.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                        acc.setAccount_type(rs.getString("account_type"));
                        return acc;
                    }
            );

            return account;
        }catch (DataAccessException e) {
            return null;
        }
    }


}
