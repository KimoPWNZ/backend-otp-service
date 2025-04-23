package com.promoit.otp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TelegramNotificationService {
    public void sendMessage(String chatId, String text) {
        // Эмуляция отправки: просто логируем
        log.info("Telegram EMULATOR: Sending message to chatId={}: {}", chatId, text);
    }
}