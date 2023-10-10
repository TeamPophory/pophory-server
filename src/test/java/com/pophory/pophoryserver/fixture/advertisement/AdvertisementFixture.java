package com.pophory.pophoryserver.fixture.advertisement;

import com.pophory.pophoryserver.domain.advertisement.Advertisement;

public class AdvertisementFixture {

    private static final String AD_NAME = "adName";
    private static final String AD_ID = "adId";
    private static final String ANDROID_VERSION = "1.0.1";
    private static final String IOS_VERSION = "1.0.2";

    public static Advertisement createAdvertisementFixture() {
        return Advertisement.builder()
                .adName(AD_NAME)
                .adId(AD_ID)
                .androidVersion(ANDROID_VERSION)
                .iOSVersion(IOS_VERSION)
                .build();
    }
}
