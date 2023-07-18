package com.pophory.pophoryserver.domain.version;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceVersionJpaRepository extends JpaRepository<ServiceVersion, Long> {

    Optional<ServiceVersion> findByOsType(OSType osType);
}
