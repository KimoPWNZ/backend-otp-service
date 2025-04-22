package com.promoit.otp.model;

import com.promoit.otp.model.enums.OtpStatus;
import java.time.LocalDateTime;

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

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOperationId() {
        return operationId;
    }
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public OtpStatus getStatus() {
        return status;
    }
    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    // --- Optional: isExpired helper ---
    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }
}