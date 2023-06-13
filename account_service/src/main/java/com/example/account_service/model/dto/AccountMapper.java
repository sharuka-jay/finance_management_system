package com.example.account_service.model.dto;

import com.example.account_service.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setAccount_id(rs.getInt("account_id"));
        account.setUser_id(rs.getInt("user_id"));
        account.setAccount_number(rs.getString("account_number"));
        account.setAccount_name(rs.getString("account_name"));
        account.setAccount_type(rs.getString("account_type"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        account.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        account.setTime_period(rs.getInt("time_period"));
        return account;
    }
}
