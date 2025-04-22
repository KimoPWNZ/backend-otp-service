package com.promoit.otp.dao;

import com.promoit.otp.model.OtpCode;
import com.promoit.otp.model.enums.OtpStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OtpCodeDao {
    void save(OtpCode otpCode);
    OtpCode findActiveByUserAndOperation(Long userId, String operationId);
    void updateStatus(Long otpId, OtpStatus status);
    int expireOtps(LocalDateTime now);
    List<OtpCode> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}