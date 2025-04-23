package com.promoit.otp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmppNotificationService {
    public void sendSms(String phone, String text) {
        // Эмуляция отправки: просто логируем
        log.info("SMPP EMULATOR: Sending SMS to {}: {}", phone, text);
    }
}