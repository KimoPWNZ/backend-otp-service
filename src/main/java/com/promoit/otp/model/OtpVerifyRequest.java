package com.promoit.otp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtpVerifyRequest {
    private Long userId;
    private String operationId;
    private String code;
}