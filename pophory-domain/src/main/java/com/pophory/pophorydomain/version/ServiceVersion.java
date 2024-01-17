package com.pophory.pophorydomain.version;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor
public class ServiceVersion {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "os_type")
    @Enumerated(STRING)
    private OSType osType;

    private String version;

    public void updateVersion(String version) {
        this.version = version;
    }

    @Builder
    public ServiceVersion(OSType osType, String version) {
        this.osType = osType;
        this.version = version;
    }
}
