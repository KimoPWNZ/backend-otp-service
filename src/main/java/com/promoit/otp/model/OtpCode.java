package com.promoit.otp.model;

import com.promoit.otp.model.enums.OtpStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OtpCode {
    private Long id;
    private Long userId;
    private String operationId;
    private String code;
    private LocalDateTime expiresAt;
    private OtpStatus status;

    public OtpCode() {}

    public OtpCode(Long userId, String operationId, String code, LocalDateTime expiresAt) {
        this.userId = userId;
        this.operationId = operationId;
        this.code = code;
        this.expiresAt = expiresAt;
        this.status = OtpStatus.ACTIVE;
    }

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }
}