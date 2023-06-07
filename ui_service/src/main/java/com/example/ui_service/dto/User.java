package com.example.ui_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int user_id;
    private String user_name;
    private String email;
    private String password;
}