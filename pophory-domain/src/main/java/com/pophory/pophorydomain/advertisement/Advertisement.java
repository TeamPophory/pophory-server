package com.pophory.pophorydomain.advertisement;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class               Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adId;

    @Column(name = "ios_version")

    private String iOSVersion;

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
