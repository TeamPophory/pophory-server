package com.pophory.pophoryserver.domain.advertisement;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Advertisement {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String adId;

    @Column(name = "ios_version")

    private String iOSVersion;

    @Column(name = "android_version")
    private String androidVersion;

    private String adName;

    @Builder
    public Advertisement(String adId, String iOSVersion, String androidVersion, String adName) {
        this.adId = adId;
        this.iOSVersion = iOSVersion;
        this.androidVersion = androidVersion;
        this.adName = adName;
    }
}
