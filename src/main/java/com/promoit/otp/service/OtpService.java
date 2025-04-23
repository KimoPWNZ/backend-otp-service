package com.promoit.otp.service;

import com.promoit.otp.model.OtpCode;
import com.promoit.otp.model.OtpConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {
    private final ConcurrentHashMap<String, OtpCode> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final OtpConfig config;
    private final EmailNotificationService emailService;
    private final SmppNotificationService smppService;
    private final TelegramNotificationService telegramService;
    private final FileOtpLogger fileOtpLogger;

    public OtpCode generateOtp(Long userId, String operationId, String email, String phone, String chatId) {
        int length = config.getCodeLength();
        int max = (int)Math.pow(10, length);
        String code = String.format("%0" + length + "d", random.nextInt(max));
        OtpCode otp = new OtpCode(userId, operationId, code, LocalDateTime.now().plusSeconds(config.getTtlSeconds()));
        otpStore.put(userId + ":" + operationId, otp);

        // Рассылка по всем каналам
        emailService.sendEmail(email, "Your OTP Code", "Your OTP code: " + code);
        smppService.sendSms(phone, "Your OTP code: " + code);
        telegramService.sendMessage(chatId, "Your OTP code: " + code);
        fileOtpLogger.logOtp(otp);

        log.info("OTP generated for userId={}, operationId={}", userId, operationId);
        return otp;
    }

    public boolean validateOtp(Long userId, String operationId, String code) {
        OtpCode otp = otpStore.get(userId + ":" + operationId);
        if (otp == null) {
            log.warn("OTP not found for userId={}, operationId={}", userId, operationId);
            return false;
        }
        if (otp.isExpired()) {
            log.warn("OTP expired for userId={}, operationId={}", userId, operationId);
            otpStore.remove(userId + ":" + operationId);
            return false;
        }
        boolean valid = otp.getCode().equals(code);
        if (valid) {
            otpStore.remove(userId + ":" + operationId);
            log.info("OTP validated for userId={}, operationId={}", userId, operationId);
        } else {
            log.warn("Invalid OTP for userId={}, operationId={}", userId, operationId);
        }
        return valid;
    }
}