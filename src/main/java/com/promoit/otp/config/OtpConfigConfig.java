package com.promoit.otp.config;

import com.promoit.otp.model.OtpConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtpConfigConfig {
    @Bean
    public OtpConfig otpConfig() {
        OtpConfig config = new OtpConfig();
        config.setCodeLength(6);      // длина кода по умолчанию
        config.setTtlSeconds(300);    // TTL по умолчанию (5 мин)
        return config;
    }
}