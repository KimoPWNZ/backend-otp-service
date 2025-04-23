package com.promoit.otp.service;

import com.promoit.otp.model.OtpCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class FileOtpLogger {
    private final String filePath = "otp_codes.log";

    public void logOtp(OtpCode otpCode) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(String.format(
                    "UserId: %d, OperationId: %s, Code: %s, Expires: %s%n",
                    otpCode.getUserId(),
                    otpCode.getOperationId(),
                    otpCode.getCode(),
                    otpCode.getExpiresAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            ));
            log.info("OTP logged to file for user {}", otpCode.getUserId());
        } catch (IOException e) {
            log.error("Failed to write OTP to log file", e);
            throw new RuntimeException("Failed to write OTP to log file", e);
        }
    }
}