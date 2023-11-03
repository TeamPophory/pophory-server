package com.pophory.pophorydomain.version.infrastructure;

import com.pophory.pophorydomain.version.OSType;
import com.pophory.pophorydomain.version.ServiceVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceVersionJpaRepository extends JpaRepository<ServiceVersion, Long> {

    Optional<ServiceVersion> findByOsType(OSType osType);
}
