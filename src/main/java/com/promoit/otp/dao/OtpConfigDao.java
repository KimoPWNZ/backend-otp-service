package com.promoit.otp.dao;

import com.promoit.otp.model.OtpConfig;

public interface OtpConfigDao {
    OtpConfig getConfig();
    void updateConfig(OtpConfig config);
}