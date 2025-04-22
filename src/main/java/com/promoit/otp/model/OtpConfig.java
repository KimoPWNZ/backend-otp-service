package com.promoit.otp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtpConfig {
    private Long id;
    private int codeLength;
    private int ttlSeconds;
}