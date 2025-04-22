package com.promoit.otp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private String login;
    private String passwordHash;
    private String role;
}