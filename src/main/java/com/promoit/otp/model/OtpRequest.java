package com.promoit.otp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtpRequest {
    private Long userId;
    private String operationId;

}