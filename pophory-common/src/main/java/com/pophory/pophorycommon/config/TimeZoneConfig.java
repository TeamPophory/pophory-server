package com.pophory.pophorycommon.config;

import jakarta.annotation.PostConstruct;

import java.util.TimeZone;

public class TimeZoneConfig {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}