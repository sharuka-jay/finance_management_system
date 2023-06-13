package com.example.account_service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {
    private int transaction_id;
    private int account_id;
    private String source;
    private String transaction_type;
    private BigDecimal amount;
    private LocalDateTime created_at;
}
