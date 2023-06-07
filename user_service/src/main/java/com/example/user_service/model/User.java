package com.example.user_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int user_id;
    private String user_name;
    private String user_email;
    private String password;
}
