package com.pophory.pophoryserver.domain.version;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Entity
@Getter
public class ServiceVersion {

    @Id @GeneratedValue
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
