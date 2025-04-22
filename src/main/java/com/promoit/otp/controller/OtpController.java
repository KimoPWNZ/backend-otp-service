package com.promoit.otp.controller;

import com.promoit.otp.model.OtpRequest;
import com.promoit.otp.model.OtpVerifyRequest;
import com.promoit.otp.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private static final Logger log = LoggerFactory.getLogger(OtpController.class);
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateOtp(@RequestBody OtpRequest request) {
        log.info("Generate OTP for userId={}, operationId={}", request.getUserId(), request.getOperationId());
        var otpCode = otpService.generateOtp(request.getUserId(), request.getOperationId());
        return ResponseEntity.ok().body(new OtpResponse(otpCode.getCode()));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateOtp(@RequestBody OtpVerifyRequest request) {
        log.info("Validate OTP for userId={}, operationId={}, code={}", request.getUserId(), request.getOperationId(), request.getCode());
        boolean valid = otpService.validateOtp(request.getUserId(), request.getOperationId(), request.getCode());
        if (valid) {
            return ResponseEntity.ok().body(new StatusResponse("OTP valid"));
        } else {
            return ResponseEntity.badRequest().body(new StatusResponse("OTP invalid or expired"));
        }
    }

    static class OtpResponse {
        private String otp;
        public OtpResponse(String otp) { this.otp = otp; }
        public String getOtp() { return otp; }
    }

    static class StatusResponse {
        private String status;
        public StatusResponse(String status) { this.status = status; }
        public String getStatus() { return status; }
    }
}