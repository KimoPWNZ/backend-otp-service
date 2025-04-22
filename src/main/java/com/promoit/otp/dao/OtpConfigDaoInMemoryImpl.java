package com.promoit.otp.dao;

import com.promoit.otp.model.OtpConfig;

public class OtpConfigDaoInMemoryImpl implements OtpConfigDao {
    private OtpConfig config;

    public OtpConfigDaoInMemoryImpl() {
        this.config = new OtpConfig();
        this.config.setId(1L);
        this.config.setCodeLength(6);
        this.config.setTtlSeconds(120);
    }

    @Override
    public OtpConfig getConfig() {
        return config;
    }

    @Override
    public void updateConfig(OtpConfig config) {
        this.config = config;
    }
}