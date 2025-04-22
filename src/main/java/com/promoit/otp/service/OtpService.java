package com.promoit.otp.service;

import com.promoit.otp.model.OtpCode;
import com.promoit.otp.model.OtpConfig;
import com.promoit.otp.model.enums.OtpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final ConcurrentHashMap<String, OtpCode> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final OtpConfig config;

    public OtpService(OtpConfig config) {
        this.config = config;
    }

    public OtpCode generateOtp(Long userId, String operationId) {
        int length = config.getCodeLength();
        int max = (int)Math.pow(10, length);
        String code = String.format("%0" + length + "d", random.nextInt(max));
        OtpCode otp = new OtpCode(userId, operationId, code, LocalDateTime.now().plusSeconds(config.getTtlSeconds()));
        otpStore.put(userId + ":" + operationId, otp);
        return otp;
    }

    public boolean validateOtp(Long userId, String operationId, String code) {
        OtpCode otp = otpStore.get(userId + ":" + operationId);
        if (otp == null) return false;
        if (otp.isExpired()) {
            otp.setStatus(OtpStatus.EXPIRED);
            otpStore.remove(userId + ":" + operationId);
            return false;
        }
        boolean valid = otp.getCode().equals(code);
        if (valid) {
            otp.setStatus(OtpStatus.USED);
            otpStore.remove(userId + ":" + operationId);
        }
        return valid;
    }
}