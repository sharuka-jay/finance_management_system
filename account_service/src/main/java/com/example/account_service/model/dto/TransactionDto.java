package com.example.account_service.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDto {
    private String account_number;
    private String tranType;
    private BigDecimal amount;
    private int user_id;
    private int account_id;
}
