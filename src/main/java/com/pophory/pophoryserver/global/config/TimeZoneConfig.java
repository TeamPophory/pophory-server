package com.pophory.pophoryserver.global.config;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

public class TimeZoneConfig {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}