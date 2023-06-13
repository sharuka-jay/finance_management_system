package com.example.account_service.model.dto;

import com.example.account_service.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(rs.getInt("transaction_id"));
        transaction.setAccount_id(rs.getInt("account_id"));
        transaction.setSource(rs.getString("source"));
        transaction.setTransaction_type(rs.getString("transaction_type"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        return transaction;
    }
}
