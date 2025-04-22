package com.promoit.otp.dao;

import com.promoit.otp.model.OtpCode;
import com.promoit.otp.model.enums.OtpStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class OtpCodeDaoInMemoryImpl implements OtpCodeDao {
    private final Map<Long, OtpCode> storage = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @Override
    public void save(OtpCode otpCode) {
        if (otpCode.getId() == null) {
            otpCode.setId(idGen.getAndIncrement());
        }
        storage.put(otpCode.getId(), otpCode);
    }

    @Override
    public OtpCode findActiveByUserAndOperation(Long userId, String operationId) {
        return storage.values().stream()
                .filter(o -> Objects.equals(o.getUserId(), userId))
                .filter(o -> Objects.equals(o.getOperationId(), operationId))
                .filter(o -> o.getStatus() == OtpStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateStatus(Long otpId, OtpStatus status) {
        OtpCode code = storage.get(otpId);
        if (code != null) {
            code.setStatus(status);
        }
    }

    @Override
    public int expireOtps(LocalDateTime now) {
        int expired = 0;
        for (OtpCode code : storage.values()) {
            if (code.getStatus() == OtpStatus.ACTIVE && code.getExpiresAt().isBefore(now)) {
                code.setStatus(OtpStatus.EXPIRED);
                expired++;
            }
        }
        return expired;
    }

    @Override
    public List<OtpCode> findByUserId(Long userId) {
        return storage.values().stream()
                .filter(o -> Objects.equals(o.getUserId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUserId(Long userId) {
        storage.values().removeIf(o -> Objects.equals(o.getUserId(), userId));
    }
}